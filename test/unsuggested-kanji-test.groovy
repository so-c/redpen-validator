package soc.com.github.redpenvalidator

import org.junit.Test
import static org.junit.Assert.*
import cc.redpen.config.ValidatorConfiguration
import cc.redpen.validator.JavaScriptValidator

/**
 * Precondition: add redpen-core-1.4.jar, slf4j-api-1.7.6.jar to ClassPath from REDPEN_HOME\lib
 *               run in home of this repository.
 */
class UnsuggestedKanjiTest {
  @Test
  void test_javascript_validators_are_loaded() {
    def vc = new ValidatorConfiguration("JavaScriptValidator")
                .addAttribute("script-path", System.getProperty("user.dir") + /\src/)
    JavaScriptValidator sut = new JavaScriptValidator()
    sut.preInit(vc, null)
    
    assertEquals(1, sut.scripts.size())
  }
}