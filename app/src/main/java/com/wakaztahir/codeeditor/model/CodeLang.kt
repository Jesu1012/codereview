package com.wakaztahir.codeeditor.model

import androidx.compose.ui.graphics.Color
import com.wakaztahir.codeeditor.prettify.lang.LangAppollo
import com.wakaztahir.codeeditor.prettify.lang.LangBasic
import com.wakaztahir.codeeditor.prettify.lang.LangClj
import com.wakaztahir.codeeditor.prettify.lang.LangCss
import com.wakaztahir.codeeditor.prettify.lang.LangDart
import com.wakaztahir.codeeditor.prettify.lang.LangErlang
import com.wakaztahir.codeeditor.prettify.lang.LangEx
import com.wakaztahir.codeeditor.prettify.lang.LangGo
import com.wakaztahir.codeeditor.prettify.lang.LangHs
import com.wakaztahir.codeeditor.prettify.lang.LangKotlin
import com.wakaztahir.codeeditor.prettify.lang.LangLasso
import com.wakaztahir.codeeditor.prettify.lang.LangLisp
import com.wakaztahir.codeeditor.prettify.lang.LangLlvm
import com.wakaztahir.codeeditor.prettify.lang.LangLogtalk
import com.wakaztahir.codeeditor.prettify.lang.LangLua
import com.wakaztahir.codeeditor.prettify.lang.LangMatlab
import com.wakaztahir.codeeditor.prettify.lang.LangMd
import com.wakaztahir.codeeditor.prettify.lang.LangMl
import com.wakaztahir.codeeditor.prettify.lang.LangMumps
import com.wakaztahir.codeeditor.prettify.lang.LangN
import com.wakaztahir.codeeditor.prettify.lang.LangPascal
import com.wakaztahir.codeeditor.prettify.lang.LangR
import com.wakaztahir.codeeditor.prettify.lang.LangRd
import com.wakaztahir.codeeditor.prettify.lang.LangScala
import com.wakaztahir.codeeditor.prettify.lang.LangSql
import com.wakaztahir.codeeditor.prettify.lang.LangSwift
import com.wakaztahir.codeeditor.prettify.lang.LangTcl
import com.wakaztahir.codeeditor.prettify.lang.LangTex
import com.wakaztahir.codeeditor.prettify.lang.LangVb
import com.wakaztahir.codeeditor.prettify.lang.LangVhdl
import com.wakaztahir.codeeditor.prettify.lang.LangWiki
import com.wakaztahir.codeeditor.prettify.lang.LangXq
import com.wakaztahir.codeeditor.prettify.lang.LangYaml
import com.wakaztahir.codeeditor.prettify.parser.Prettify
import com.wakaztahir.codeeditor.theme.Displayable

enum class CodeLang(
    val langProvider: Prettify.LangProvider?,
    val value: List<String>,
    override val displayName: String,
    override val color: Color
) : Displayable {
    Default(null, listOf("default-code"), "Default", Color(0xFFBDBDBD)),
    HTML(null, listOf("default-markup"), "HTML", Color(0xFFE34C26)),
    C(null, listOf("c"), "C", Color(0xFF555555)),
    CPP(null, listOf("cpp"), "C++", Color(0xFF004482)),
    ObjectiveC(null, listOf("cxx"), "Objective-C", Color(0xFF438EFF)),
    CSharp(null, listOf("cs"), "C#", Color(0xFF178600)),
    Java(null, listOf("java"), "Java", Color(0xFFB07219)),
    Bash(null, listOf("bash"), "Bash", Color(0xFF89E051)),
    Python(null, listOf("python"), "Python", Color(0xFF3572A5)),
    Perl(null, listOf("perl"), "Perl", Color(0xFF0298C3)),
    Ruby(null, listOf("ruby"), "Ruby", Color(0xFF701516)),
    JavaScript(null, listOf("javascript"), "JavaScript", Color(0xFFF1E05A)),
    CoffeeScript(null, listOf("coffee"), "CoffeeScript", Color(0xFF244776)),
    Rust(null, listOf("rust"), "Rust", Color(0xFFDEA584)),
    OCAML(null, listOf("ml"), "OCaml", Color(0xFF3BE133)),
    SML(null, listOf("ml"), "SML", Color(0xFF3BE133)),
    FSharp(null, listOf("fs"), "F#", Color(0xFFB845FC)),
    JSON(null, listOf("json"), "JSON", Color(0xFF292929)),
    XML(null, listOf("xml"), "XML", Color(0xFF0060AC)),
    Proto(null, listOf("proto"), "Proto", Color(0xFF438EFF)),
    RegEx(null, listOf("regex"), "RegEx", Color(0xFF0098FF)),
    Appollo({ LangAppollo() }, LangAppollo.fileExtensions, "Apollo", Color(0xFF00ADD8)),
    Basic({ LangBasic() }, LangBasic.fileExtensions, "Basic", Color(0xFF00ADD8)),
    Clojure({ LangClj() }, LangClj.fileExtensions, "Clojure", Color(0xFFDB5855)),
    CSS({ LangCss() }, LangCss.fileExtensions, "CSS", Color(0xFF563D7C)),
    Dart({ LangDart() }, LangDart.fileExtensions, "Dart", Color(0xFF00B4AB)),
    Erlang({ LangErlang() }, LangErlang.fileExtensions, "Erlang", Color(0xFFB83998)),
    Go({ LangGo() }, LangGo.fileExtensions, "Go", Color(0xFF375EAB)),
    Haskell({ LangHs() }, LangHs.fileExtensions, "Haskell", Color(0xFF5E5086)),
    Lisp({ LangLisp() }, LangLisp.fileExtensions, "Lisp", Color(0xFFC65D28)),
    Llvm({ LangLlvm() }, LangLlvm.fileExtensions, "LLVM", Color(0xFF185619)),
    Lua({ LangLua() }, LangLua.fileExtensions, "Lua", Color(0xFF000080)),
    Matlab({ LangMatlab() }, LangMatlab.fileExtensions, "Matlab", Color(0xFFBB92AC)),
    ML({ LangMl() }, LangMl.fileExtensions, "ML", Color(0xFF3BE133)),
    Mumps({ LangMumps() }, LangMumps.fileExtensions, "Mumps", Color(0xFF001800)),
    N({ LangN() }, LangN.fileExtensions, "N", Color(0xFF9DFF00)),
    Pascal({ LangPascal() }, LangPascal.fileExtensions, "Pascal", Color(0xFF0091BD)),
    R({ LangR() }, LangR.fileExtensions, "R", Color(0xFF198CE7)),
    Rd({ LangRd() }, LangRd.fileExtensions, "Rd", Color(0xFF198CE7)),
    Scala({ LangScala() }, LangScala.fileExtensions, "Scala", Color(0xFFDC322F)),
    SQL({ LangSql() }, LangSql.fileExtensions, "SQL", Color(0xFFCC0073)),
    Tex({ LangTex() }, LangTex.fileExtensions, "TeX", Color(0xFF3D6117)),
    VB({ LangVb() }, LangVb.fileExtensions, "VB", Color(0xFF945DB7)),
    VHDL({ LangVhdl() }, LangVhdl.fileExtensions, "VHDL", Color(0xFFADB2CB)),
    Tcl({ LangTcl() }, LangTcl.fileExtensions, "Tcl", Color(0xFFE4CC98)),
    Wiki({ LangWiki() }, LangWiki.fileExtensions, "Wiki", Color(0xFFFCF4DC)),
    XQuery({ LangXq() }, LangXq.fileExtensions, "XQuery", Color(0xFF523B1B)),
    YAML({ LangYaml() }, LangYaml.fileExtensions, "YAML", Color(0xFFD3D3D3)),
    Markdown({ LangMd() }, LangMd.fileExtensions, "Markdown", Color(0xFF083FA1)),
    Ex({ LangEx() }, LangEx.fileExtensions, "Elixir", Color(0xFF6E4A7E)),
    Kotlin({ LangKotlin() }, LangKotlin.fileExtensions, "Kotlin", Color(0xFFF18E33)),
    Lasso({ LangLasso() }, LangLasso.fileExtensions, "Lasso", Color(0xFF999999)),
    Logtalk({ LangLogtalk() }, LangLogtalk.fileExtensions, "Logtalk", Color(0xFF295B9A)),
    Swift({ LangSwift() }, LangSwift.fileExtensions, "Swift", Color(0xFFF05138))
}
