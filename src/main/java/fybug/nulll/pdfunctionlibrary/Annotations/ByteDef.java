package fybug.nulll.pdfunctionlibrary.Annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h2>指定字节常量列表.</h2>
 * <p>该注释解指定该使用的值</p>
 *
 * @author fybug
 * @version 0.0.2
 * @since PDF 1.2
 */
@Retention( RetentionPolicy.SOURCE )
@Target( {ElementType.ANNOTATION_TYPE} )
public
@interface ByteDef {
    /** <p>应使用的常量.</p> */
    byte[] value() default {};
}
