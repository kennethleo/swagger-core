package converter

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.swagger.annotations.ApiModelProperty
import io.swagger.converter.ModelConverters
import io.swagger.models.properties.{DateProperty, StringProperty}
import io.swagger.util.Json
import org.joda.time.LocalDate
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

import scala.annotation.meta.field

@RunWith(classOf[JUnitRunner])
class JodaLocalDateConverterTest extends FlatSpec with Matchers {
  val m = Json.mapper()
  m.registerModule(DefaultScalaModule)

  it should "read a generic model" in {
    val models = ModelConverters.getInstance().read(classOf[ModelWithJodaLocalDate])
    models.size should be(1)

    val model = models.get("ModelWithJodaLocalDate")

    val dateTimeProperty = model.getProperties().get("createdAt")
    dateTimeProperty.isInstanceOf[DateProperty] should be(true)
    dateTimeProperty.getPosition should be(1)
    dateTimeProperty.getRequired should be(true)
    dateTimeProperty.getDescription should be("creation localDate")

    val nameProperty = model.getProperties().get("name")
    nameProperty.isInstanceOf[StringProperty] should be(true)
    nameProperty.getPosition should be(2)
    nameProperty.getDescription should be("name of the model")
  }
}


case class ModelWithJodaLocalDate(
                                   @(ApiModelProperty@field)(value = "name of the model", position = 2) name: String,
                                   @(ApiModelProperty@field)(value = "creation localDate", required = true, position = 1) createdAt: LocalDate)
