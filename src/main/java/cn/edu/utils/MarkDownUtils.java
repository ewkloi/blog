package cn.edu.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.html.HtmlWriter;
import org.springframework.util.IdGenerator;

public class MarkDownUtils {

  /**
   * markdown格式转换成HTML格式
   */
  public static String markdownToHtml(String markdown) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(markdown);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
// "<p>This is <em>Sparta</em></p>\n"
    return renderer.render(document);
  }

  /**
   * 增加扩展[标题锚点，表格生成] Markdown转换成HTML
   */
  public static String getHtmlContent(String content) {
    Set<Extension> headingextensions = Collections.singleton(HeadingAnchorExtension.create());
    List<Extension> tableextensions = Arrays.asList(TablesExtension.create());
//    解析
    Parser parser = Parser.builder()
        .extensions(tableextensions)
        .build();
    Node node = parser.parse(content);
//    渲染
    HtmlRenderer renderer = HtmlRenderer.builder()
        .extensions(headingextensions)
        .extensions(tableextensions)
        .attributeProviderFactory(new AttributeProviderFactory() {
          @Override
          public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
            return new TagAttributeProvider();
          }
        })
//        .nodeRendererFactory(new HtmlNodeRendererFactory() {
//          public NodeRenderer create(HtmlNodeRendererContext context) {
//            return new IndentedCodeBlockNodeRenderer(context);
//          }
//        })
        .build();

    return renderer.render(node);
  }

  //  给标签添加属性
  static class TagAttributeProvider implements AttributeProvider {

    @Override
    public void setAttributes(Node node, String s, Map<String, String> map) {
      if (node instanceof TableBlock) {
        map.put("class", "ui celled table");
      }
      if (node instanceof Link) {
        map.put("target", "_blank");
      }
    }
  }

  static class IndentedCodeBlockNodeRenderer implements NodeRenderer {

    private final HtmlWriter html;

    IndentedCodeBlockNodeRenderer(HtmlNodeRendererContext context) {
      this.html = context.getWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
      // Return the node types we want to use this renderer for.
      return Collections.<Class<? extends Node>>singleton(IndentedCodeBlock.class);
    }

    @Override
    public void render(Node node) {
      // We only handle one type as per getNodeTypes, so we can just cast it here.
      IndentedCodeBlock codeBlock = (IndentedCodeBlock) node;
      html.line();
      html.tag("pre");
      html.text(codeBlock.getLiteral());
      html.tag("/pre");
      html.line();
    }
  }

  public static void main(String[] args) {
    String content = "## 这是二级标题\n"
        + "|  一 |二   |\n"
        + "| ------------ | ------------ |\n"
        + "|  三 | 四  |\n"
        + "|  五 | 六  |\n"
        + "![博客首图](https://unsplash.it/1000/400?image=1005 \"博客首图\")\n"
        + "[baidu](http://www.baidu.com \"baidu\")";
    String a = "[imCoding 爱编程](http://www.lirenmi.cn)";
    String code = "```java\n"
        + "  public static String getHtmlContent(String content){\n"
        + "    Set<Extension>  headingextensions= Collections.singleton(HeadingAnchorExtension.create());\n"
        + "    List<Extension> tableextensions = Arrays.asList(TablesExtension.create());\n"
        + "//    解析\n"
        + "    Parser parser = Parser.builder()\n"
        + "        .extensions(tableextensions)\n"
        + "        .build();\n"
        + "    Node node=parser.parse(content);\n"
        + "//    渲染\n"
        + "    HtmlRenderer renderer = HtmlRenderer.builder()\n"
        + "        .extensions(headingextensions)\n"
        + "        .extensions(tableextensions)\n"
        + "        .attributeProviderFactory(new AttributeProviderFactory() {\n"
        + "          @Override\n"
        + "          public AttributeProvider create(AttributeProviderContext attributeProviderContext) {\n"
        + "            return new TagAttributeProvider();\n"
        + "          }\n"
        + "        })\n"
        + "        .build();\n"
        + "\n"
        + "    return  renderer.render(node);\n"
        + "  }\n"
        + "//  给标签添加属性\n"
        + "  static class TagAttributeProvider implements AttributeProvider{\n"
        + "    @Override\n"
        + "    public void setAttributes(Node node, String s, Map<String, String> map) {\n"
        + "      if (node instanceof TableBlock){\n"
        + "        map.put(\"class\",\"ui celled table\");\n"
        + "      }\n"
        + "      if (node instanceof Link){\n"
        + "        map.put(\"target\",\"_blank\");\n"
        + "      }\n"
        + "    }\n"
        + "  }\n"
        + "```";
    System.out.println(getHtmlContent(code));
  }
}

