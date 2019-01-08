package fybug.nulll.pdfunctionlibrary.Annotations;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <h2>测试包.</h2>
 * <p>用于标识该包是测试版本的包</p>
 *
 * @author fybug
 * @version 0.0.1
 * @since PDF 1.3
 */
@Documented
@Retention( RetentionPolicy.SOURCE )
@Target( {ElementType.PACKAGE} )
public
@interface TestPackage {}