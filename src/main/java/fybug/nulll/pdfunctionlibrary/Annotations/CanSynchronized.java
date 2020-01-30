package fybug.nulll.pdfunctionlibrary.Annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h2>可并发标识</h2>
 * 使用该类注解类标识 <b>该方法</b> 或 <b>该类下的方法</b> 是否可用于并发
 *
 * 静态方法理应单独标记
 *
 * 代表标识范围的方法支持并发
 *
 * @author fybug
 * @version 0.0.2
 * @see NoSynchronized
 * @since PDF 1.2
 */
@Retention( RetentionPolicy.CLASS )
@Target( {ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD, ElementType.PACKAGE,
                 ElementType.TYPE_USE} )
public
@interface CanSynchronized {}
