package com.izforge.izpack.compiler

import com.izforge.izpack.util.substitutor.VariableSubstitutor
import helper.CompilerHelper
import helper.impl.XmlCompilerHelper
import data.{PropertyManager, CompilerData}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.fixture.{FixtureFlatSpec}
import com.izforge.izpack.adaptator.impl.XMLParser
import org.mockito.Mockito
import java.util.Properties

/**
 * Created by IntelliJ IDEA.
 * @author Anthonin Bonnefoy
 */

class CompilerConfigScalaTest extends FixtureFlatSpec with ShouldMatchers with MockitoSugar {

  // 1. define type FixtureParam
  type FixtureParam = CompilerConfig

  var data = mock[CompilerData]
  var variableSubstitutor = mock[VariableSubstitutor]
  var compiler = mock[Compiler]
  var compilerHelper = mock[CompilerHelper]
  var xmlCompilerHerlper = mock[XmlCompilerHelper]
  var propertyManager = mock[PropertyManager]
  val xmlParser = new XMLParser
  // 2. define the withFixture method
  def withFixture(test: OneArgTest) {
    val compilerConfig = new CompilerConfig(data, variableSubstitutor, compiler, compilerHelper, xmlCompilerHerlper, propertyManager)
    test(compilerConfig)
  }

  "The compilerConfig" should "add variables" in {
    compilerConfig: CompilerConfig =>
      val xmlData = xmlParser.parse(<root>
        <variables>
            <variable name="scriptFile" value="script.bat"/>
        </variables>
      </root> + "")
      val variable = mock[Properties]
      Mockito.when(compiler.getVariables).thenReturn(variable)

      compilerConfig.addVariables(xmlData)

      Mockito.verify(variable).setProperty("scriptFile", "script.bat")
  }


}