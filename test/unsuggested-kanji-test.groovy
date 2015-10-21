package soc.com.github.redpenvalidator

import org.junit.Test
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*

import cc.redpen.RedPen;
import cc.redpen.validator.JavaScriptValidator

import cc.redpen.config.ValidatorConfiguration
import cc.redpen.config.Configuration

import cc.redpen.parser.DocumentParser
import cc.redpen.parser.SentenceExtractor;

/**
 * Precondition: add following jars to ClassPath from REDPEN_HOME\lib:
 *               run in home of this repository.
 */
class UnsuggestedKanjiTest {
  def vc = new ValidatorConfiguration("JavaScript")
              .addAttribute("script-path", System.getProperty("user.dir") + File.separator  + "src")
  def conf = new Configuration.ConfigurationBuilder()
                              .addValidatorConfig(vc)
                              .setLanguage("ja")
                              .build()

  @Test
  void test_javascript_validators_are_loaded() {
    // setup
    def sut = new JavaScriptValidator()
    sut.preInit(vc, null)
    
    // verify
    assertThat sut.scripts.size(), is(1)
  }
  
  @Test 
  void test_check_事() {
    // setup
    def parser = DocumentParser.PLAIN
    def doc = parser.parse("知らない事がある。", 
                           new SentenceExtractor(conf.getSymbolTable()),
                           conf.getTokenizer())
     
    // exercise
    def errors = (new RedPen(conf)).validate([doc]).get(doc)
     
    // verify
    assertThat errors.size(), is(1)
    assertThat errors.get(0).getMessage(), is("[unsuggested-kanji.js] 推奨しない漢字が「事」で使われています。")
  }
  
    @Test 
  void test_not_check_事() {
    // setup
    def parser = DocumentParser.PLAIN
    def doc = parser.parse("仕事がある。時事問題。事を急ぐ。急いては事をし損じる。", 
                           new SentenceExtractor(conf.getSymbolTable()),
                           conf.getTokenizer())
     
    // exercise
    def errors = (new RedPen(conf)).validate([doc]).get(doc)
     
    // verify
    assertThat errors.size(), is(0)
  }
}