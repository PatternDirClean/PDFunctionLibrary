package fybug.nulll.pdfunctionlibrary.Processing.Stop;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <h2>表示该方法或对象或许会中断.</h2>
 * <p>执行该方法的时候可能会中断,或者该类的方法都应该会中断<br/>
 * 中断时应抛出 {@link Stop}<br/>
 * 该注释标识该方法可能发生中断，调用者应注意这点</p>
 *
 * @author fybug
 * @version 0.0.4
 * @see Stop
 * @since PDF 1.1
 */
@Documented
@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.METHOD, ElementType.TYPE} )
public
@interface MaybeStop {}