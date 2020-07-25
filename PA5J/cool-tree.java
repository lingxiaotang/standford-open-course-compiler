// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.io.PrintStream;
import java.util.Vector;



/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

    public abstract void semant();

    public abstract void cgen(PrintStream s);

}

/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

    public abstract AbstractSymbol getName();

    public abstract AbstractSymbol getParent();

    public abstract AbstractSymbol getFilename();

    public abstract Features getFeatures();

}

/**
 * Defines list phylum Classes
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}

/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

    public abstract void code(CgenNode classNode, CgenClassTable classTable, PrintStream str);

    public abstract int getTempNumber();

}

/**
 * Defines list phylum Features
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}

/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

}

/**
 * Defines list phylum Formals
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}

/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }

    private AbstractSymbol type = null;

    public AbstractSymbol get_type() {
        return type;
    }

    public Expression set_type(AbstractSymbol s) {
        type = s;
        return this;
    }

    public abstract void dump_with_types(PrintStream out, int n);

    public void dump_type(PrintStream out, int n) {
        if (type != null) {
            out.println(Utilities.pad(n) + ": " + type.getString());
        } else {
            out.println(Utilities.pad(n) + ": _no_type");
        }
    }

    public abstract void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str);

    public abstract int getTempNumber();
}

/**
 * Defines list phylum Expressions
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}

/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }

    public abstract void dump_with_types(PrintStream out, int n);

    public abstract void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str);

    public abstract int getTempNumber();
}

/**
 * Defines list phylum Cases
 * <p>
 * See <a href="ListNode.html">ListNode</a> for full documentation.
 */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;

    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }

    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }

    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }

    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }

    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}

/**
 * Defines AST constructor 'program'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class program extends Program {
    public Classes classes;

    /**
     * Creates "program" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for classes
     */
    public program(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }

    public TreeNode copy() {
        return new program(lineNumber, (Classes) classes.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "program\n");
        classes.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements();) {
            ((Class_) e.nextElement()).dump_with_types(out, n + 1);
        }
    }

    /**
     * This method is the entry point to the semantic checker. You will need to
     * complete it in programming assignment 4.
     * <p>
     * Your checker should do the following two things:
     * <ol>
     * <li>Check that the program is semantically correct
     * <li>Decorate the abstract syntax tree with type information by setting the
     * type field in each Expression node. (see tree.h)
     * </ol>
     * <p>
     * You are free to first do (1) and make sure you catch all semantic errors.
     * Part (2) can be done in a second stage when you want to test the complete
     * compiler.
     */
    public void semant() {
        /* ClassTable constructor may do some semantic analysis */
        ClassTable classTable = new ClassTable(classes);

        /* some semantic analysis code may go here */

        if (classTable.errors()) {
            System.err.println("Compilation halted due to static semantic errors.");
            System.exit(1);
        }
    }

    /**
     * This method is the entry point to the code generator. All of the work of the
     * code generator takes place within CgenClassTable constructor.
     * 
     * @param s the output stream
     * @see CgenClassTable
     */
    public void cgen(PrintStream s) {
        // spim wants comments to start with '#'
        s.print("# start of generated code\n");

        CgenClassTable codegen_classtable = new CgenClassTable(classes, s);

        s.print("\n# end of generated code\n");
    }

}

/**
 * Defines AST constructor 'class_'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class class_ extends Class_ {
    public AbstractSymbol name;
    public AbstractSymbol parent;
    public Features features;
    public AbstractSymbol filename;

    /**
     * Creates "class_" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for parent
     * @param a2         initial value for features
     * @param a3         initial value for filename
     */
    public class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }

    public TreeNode copy() {
        return new class_(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent),
                (Features) features.copy(), copy_AbstractSymbol(filename));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_\n");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        features.dump(out, n + 2);
        dump_AbstractSymbol(out, n + 2, filename);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
            ((Feature) e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }

    public AbstractSymbol getName() {
        return name;
    }

    public AbstractSymbol getParent() {
        return parent;
    }

    public AbstractSymbol getFilename() {
        return filename;
    }

    public Features getFeatures() {
        return features;
    }

}

/**
 * Defines AST constructor 'method'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class method extends Feature {
    public AbstractSymbol name;
    public Formals formals;
    public AbstractSymbol return_type;
    public Expression expr;

    /**
     * Creates "method" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for formals
     * @param a2         initial value for return_type
     * @param a3         initial value for expr
     */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }

    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals) formals.copy(),
                copy_AbstractSymbol(return_type), (Expression) expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n + 2, name);
        formals.dump(out, n + 2);
        dump_AbstractSymbol(out, n + 2, return_type);
        expr.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            ((Formal) e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
        expr.dump_with_types(out, n + 2);
    }

    public void code(CgenNode classNode, CgenClassTable classTable, PrintStream str) {
        classTable.enterScope();
        int allTempNumber = getTempNumber();
        CgenSupport.emitEnterFunction(allTempNumber, str);

        int curTemp = allTempNumber;
        int i = 0;
        Stack S=new Stack<>();

        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
            S.push(e.nextElement());
        }

        while(!S.isEmpty()){
            int offSet = curTemp + CgenSupport.DEFAULT_OBJFIELDS + i;
            formal f = (formal) S.pop();
            classTable.addId(f.name, new Integer(offSet));
            i++;
        }

        expr.code(classNode, classTable, 0, str);
        CgenSupport.emitExitFunction(formals.getLength(), allTempNumber, str);
        classTable.exitScope();
    }

    public int getTempNumber() {
        return expr.getTempNumber();
    }

}

/**
 * Defines AST constructor 'attr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class attr extends Feature {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression init;

    /**
     * Creates "attr" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for type_decl
     * @param a2         initial value for init
     */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }

    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl),
                (Expression) init.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
    }

    public void code(CgenNode classNode, CgenClassTable classTable, PrintStream str) {
        if(init instanceof no_expr){
            return;
        }
        else{
        init.code(classNode, classTable, 0, str);
        int offSet=CgenClassTable.getAttrOffset(classNode,name);
        CgenSupport.emitStore(CgenSupport.ACC, offSet, CgenSupport.SELF, str);
        }
    }

    public int getTempNumber() {
        return init.getTempNumber();
    }

}

/**
 * Defines AST constructor 'formal'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class formal extends Formal {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;

    /**
     * Creates "formal" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for type_decl
     */
    public formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }

    public TreeNode copy() {
        return new formal(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formal\n");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}

/**
 * Defines AST constructor 'branch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class branch extends Case {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression expr;

    /**
     * Creates "branch" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for type_decl
     * @param a2         initial value for expr
     */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }

    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl),
                (Expression) expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        expr.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
        expr.dump_with_types(out, n + 2);
    }

    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        expr.code(classNode, classTable, curTemp, str);
    }

    public int getTempNumber() {
        return expr.getTempNumber();
    }
}

/**
 * Defines AST constructor 'assign'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class assign extends Expression {
    public AbstractSymbol name;
    public Expression expr;

    /**
     * Creates "assign" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     * @param a1         initial value for expr
     */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }

    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression) expr.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n + 2, name);
        expr.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
        expr.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        expr.code(classNode, classTable, curTemp, str);
        Object result = classTable.lookup(name);
        if (result != null) {
            int offSet = (int) result;
            CgenSupport.emitStore(CgenSupport.ACC, offSet, CgenSupport.FP, str);
            CgenSupport.emitAddiu(CgenSupport.A1, CgenSupport.FP, offSet, str);
            CgenSupport.emitJal("_GenGC_Assign", str);
        } else {
            int offSet = CgenClassTable.getAttrOffset(classNode, name);
            CgenSupport.emitStore(CgenSupport.ACC, offSet, CgenSupport.SELF, str);
            CgenSupport.emitAddiu(CgenSupport.A1, CgenSupport.SELF, offSet, str);
            CgenSupport.emitJal("_GenGC_Assign", str);
        }
    }

    public int getTempNumber() {
        return expr.getLineNumber();
    }

}

/**
 * Defines AST constructor 'static_dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class static_dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol type_name;
    public AbstractSymbol name;
    public Expressions actual;

    /**
     * Creates "static_dispatch" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for expr
     * @param a1         initial value for type_name
     * @param a2         initial value for name
     * @param a3         initial value for actual
     */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }

    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression) expr.copy(), copy_AbstractSymbol(type_name),
                copy_AbstractSymbol(name), (Expressions) actual.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        actual.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression) e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */

    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expression = (Expression) e.nextElement();
            expression.code(classNode, classTable, curTemp, str);
            CgenSupport.emitPush(CgenSupport.ACC, str);
        }
        
        expr.code(classNode, classTable, curTemp, str);
        int label=CgenClassTable.getLabelNumber();
        CgenSupport.emitLoadImm(CgenSupport.T1, 0, str);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, label, str);
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), str);
        int fileIndex=AbstractTable.stringtable.lookup(classNode.filename.str).index;
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX+fileIndex, str);
        CgenSupport.emitJal("_dispatch_abort", str);

        CgenSupport.emitLabelDef(label, str);
        CgenNode staticClassNode=classTable.getNode(type_name);
        int offSet=classTable.getMethodOffset(staticClassNode, name);
        CgenSupport.emitPartialLoadAddress(CgenSupport.T2, str);
        str.println(type_name+"_dispTab");
        CgenSupport.emitLoad(CgenSupport.T2, offSet,CgenSupport.T2,str);
        CgenSupport.emitJalr(CgenSupport.T2, str);
    }

    public int getTempNumber() {
        int result = expr.getTempNumber();
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expression = (Expression) e.nextElement();
            if (result < expression.getTempNumber())
                result = expression.getTempNumber();
        }
        return result;
    }

}

/**
 * Defines AST constructor 'dispatch'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol name;
    public Expressions actual;

    /**
     * Creates "dispatch" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for expr
     * @param a1         initial value for name
     * @param a2         initial value for actual
     */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }

    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression) expr.copy(), copy_AbstractSymbol(name),
                (Expressions) actual.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        actual.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
        expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            ((Expression) e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * @param str the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        AbstractSymbol thisType=null;
        if (expr instanceof no_expr||expr.get_type().equals(TreeConstants.SELF_TYPE))
        thisType=classNode.name;
        else
        thisType=expr.get_type();
         
        
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expression = (Expression) e.nextElement();
            expression.code(classNode, classTable, curTemp, str);
            CgenSupport.emitPush(CgenSupport.ACC, str);
        }

        expr.code(classNode, classTable, curTemp, str);
        int label=CgenClassTable.getLabelNumber();
        CgenSupport.emitLoadImm(CgenSupport.T1, 0, str);
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, label, str);
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), str);
        int fileIndex=AbstractTable.stringtable.lookup(classNode.filename.str).index;
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX+fileIndex, str);
        CgenSupport.emitJal("_dispatch_abort", str);

        CgenSupport.emitLabelDef(label, str);
        int offSet = CgenClassTable.getMethodOffset(classTable.getNode(thisType), name);
        CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, offSet, CgenSupport.T1, str);
        CgenSupport.emitJalr(CgenSupport.T1, str);
    }

    public int getTempNumber() {
        int result = expr.getTempNumber();
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
            Expression expression = (Expression) e.nextElement();
            if (result < expression.getTempNumber())
                result = expression.getTempNumber();
        }
        return result;
    }
}

/**
 * Defines AST constructor 'cond'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class cond extends Expression {
    public Expression pred;
    public Expression then_exp;
    public Expression else_exp;

    /**
     * Creates "cond" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for pred
     * @param a1         initial value for then_exp
     * @param a2         initial value for else_exp
     */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }

    public TreeNode copy() {
        return new cond(lineNumber, (Expression) pred.copy(), (Expression) then_exp.copy(),
                (Expression) else_exp.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n + 2);
        then_exp.dump(out, n + 2);
        else_exp.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
        pred.dump_with_types(out, n + 2);
        then_exp.dump_with_types(out, n + 2);
        else_exp.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        pred.code(classNode, classTable, curTemp, str);
        CgenSupport.emitPartialLoadAddress(CgenSupport.T1, str);
        BoolConst.truebool.codeRef(str);
        str.println();
        int label1 = CgenClassTable.getLabelNumber();
        int label2 = CgenClassTable.getLabelNumber();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, label1, str);
        then_exp.code(classNode, classTable, curTemp, str);
        CgenSupport.emitBranch(label2, str);
        CgenSupport.emitLabelDef(label1, str);
        else_exp.code(classNode, classTable, curTemp, str);
        CgenSupport.emitLabelDef(label2, str);
    }

    public int getTempNumber() {
        int result1 = pred.getTempNumber();
        int result2 = then_exp.getTempNumber();
        int result3 = else_exp.getTempNumber();
        return Math.max(Math.max(result1, result2), result3);
    }

}

/**
 * Defines AST constructor 'loop'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class loop extends Expression {
    public Expression pred;
    public Expression body;

    /**
     * Creates "loop" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for pred
     * @param a1         initial value for body
     */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }

    public TreeNode copy() {
        return new loop(lineNumber, (Expression) pred.copy(), (Expression) body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n + 2);
        body.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
        pred.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        int labelNumber1 = CgenClassTable.getLabelNumber();
        int labelNumber2 = CgenClassTable.getLabelNumber();

        CgenSupport.emitLabelDef(labelNumber1, str);
        pred.code(classNode, classTable, curTemp, str);
        CgenSupport.emitPartialLoadAddress(CgenSupport.T1, str);
        BoolConst.truebool.codeRef(str);
        str.println();
        CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.T1, labelNumber2, str);
        body.code(classNode, classTable, curTemp, str);
        CgenSupport.emitBranch(labelNumber1, str);
        CgenSupport.emitLabelDef(labelNumber2, str);
        CgenSupport.emitLoadImm(CgenSupport.ACC,0,str);
    }

    public int getTempNumber() {
        return Math.max(pred.getTempNumber(), body.getTempNumber());
    }
}

/**
 * Defines AST constructor 'typcase'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class typcase extends Expression {
    public Expression expr;
    public Cases cases;

    /**
     * Creates "typcase" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for expr
     * @param a1         initial value for cases
     */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }

    public TreeNode copy() {
        return new typcase(lineNumber, (Expression) expr.copy(), (Cases) cases.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n + 2);
        cases.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
        expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            ((Case) e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        expr.code(classNode, classTable, curTemp, str);
        // 编译器推定的类型在编译时期就可以确定而动态类型需要在运行的时候才能确定
        CgenSupport.emitStore(CgenSupport.ACC, curTemp, CgenSupport.FP, str);
        int voidLabel=CgenClassTable.getLabelNumber();
        CgenSupport.emitLoadImm(CgenSupport.T2, 0, str);
        CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T2, voidLabel, str);
        
        List<branch> branchList = getSortedBranch(classNode,classTable);
        int temp_label = CgenClassTable.getLabelNumber();
        int end_label = CgenClassTable.getLabelNumber();
        int voidDispatchLabel = CgenClassTable.getLabelNumber();
        for (branch thisBranch : branchList) {
            classTable.enterScope();
            int branchTag = classTable.getTagNumber(thisBranch.type_decl);
            int lowestSubtypeTag = classTable.getLowestSubtypeTag(thisBranch.type_decl);
            CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.ACC, str);
            CgenSupport.emitBlti(CgenSupport.T1, branchTag, temp_label, str);
            CgenSupport.emitBgti(CgenSupport.T1, lowestSubtypeTag, voidDispatchLabel, str);
            classTable.addId(thisBranch.name, new Integer(curTemp));
            thisBranch.expr.code(classNode, classTable, curTemp+1, str);
            CgenSupport.emitBranch(end_label, str);
            CgenSupport.emitLabelDef(temp_label, str);
            temp_label = CgenClassTable.getLabelNumber();
            
        }
        CgenSupport.emitLabelDef(voidDispatchLabel, str);
        CgenSupport.emitJal("_case_abort", str);
        CgenSupport.emitLabelDef(voidLabel, str);
        int index=AbstractTable.stringtable.lookup(classNode.filename.str).index;
        CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX+index, str);
        CgenSupport.emitLoadImm(CgenSupport.T1, expr.getLineNumber(), str);
        CgenSupport.emitJal("_case_abort2", str);
        CgenSupport.emitLabelDef(end_label, str);
    }

    public int getTempNumber() {
        int tempNumber = 0;
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            Case thisCase = (Case) e.nextElement();
            if (thisCase.getTempNumber() > tempNumber)
                tempNumber = thisCase.getTempNumber();
        }
        return Math.max(expr.getTempNumber(), tempNumber + 1);
    }

    /**
     * choose all case branches if their types are subtypes of expr and sort them
     * according to their tagnumbers
     * 
     * @return a list contains all the sorted cases
     */
    private List<branch> getSortedBranch(CgenNode classNode,CgenClassTable classTable) {
        AbstractSymbol exprType = expr.get_type();
        if(exprType.str.equals("SELF_TYPE"))
            exprType=classNode.name;
        List<branch> sortedCases = new ArrayList<>();
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
            branch thisBranch = (branch) e.nextElement();
            AbstractSymbol branchType = thisBranch.type_decl;
            if (!branchType.str.equals("SELF_TYPE")&&(classTable.isSubtype(branchType, exprType)||classTable.isSubtype(exprType,branchType)))
            {
                sortedCases.add(thisBranch);
            }
        }

        // sort the List accoding to their tag number,put the largest in the front
        for (int i = 0; i < sortedCases.size(); i++) {
            for (int j = i + 1; j < sortedCases.size(); j++) {
                branch branch1 = (branch) sortedCases.get(i);
                branch branch2 = (branch) sortedCases.get(j);
                if (classTable.getTagNumber(branch1.type_decl) < classTable.getTagNumber(branch2.type_decl)) {
                    sortedCases.set(i, branch2);
                    sortedCases.set(j, branch1);
                }
            }
        }
        return sortedCases;
    }

}

/**
 * Defines AST constructor 'block'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class block extends Expression {
    public Expressions body;

    /**
     * Creates "block" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for body
     */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }

    public TreeNode copy() {
        return new block(lineNumber, (Expressions) body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            ((Expression) e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            Expression expr = (Expression) e.nextElement();
            expr.code(classNode, classTable, curTemp, str);
        }
    }

    public int getTempNumber() {
        int maxNumber = 0;
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
            Expression expr = (Expression) e.nextElement();
            if (expr.getTempNumber() > maxNumber)
                maxNumber = expr.getTempNumber();
        }
        return maxNumber;
    }
}

/**
 * Defines AST constructor 'let'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class let extends Expression {
    public AbstractSymbol identifier;
    public AbstractSymbol type_decl;
    public Expression init;
    public Expression body;

    /**
     * Creates "let" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for identifier
     * @param a1         initial value for type_decl
     * @param a2         initial value for init
     * @param a3         initial value for body
     */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }

    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl),
                (Expression) init.copy(), (Expression) body.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n + 2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump(out, n + 2);
        body.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
        dump_AbstractSymbol(out, n + 2, identifier);
        dump_AbstractSymbol(out, n + 2, type_decl);
        init.dump_with_types(out, n + 2);
        body.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        classTable.enterScope();
        if (init instanceof no_expr) {
            if (type_decl.str.equals("String")) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
                str.println(CgenClassTable.defaultString);
            } else if (type_decl.str.equals("Bool")) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
                BoolConst.falsebool.codeRef(str);
                str.println();
            } else if (type_decl.str.equals("Int")) {
                CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
                str.println(CgenClassTable.defaultInt);
            } else

                CgenSupport.emitLoadImm(CgenSupport.ACC, 0, str);

        } else {
            init.code(classNode, classTable, curTemp, str);
        }
        //the identifier should be added to the symbol table after the init expression
        classTable.addId(identifier, new Integer(curTemp));
        CgenSupport.emitStore(CgenSupport.ACC, curTemp, CgenSupport.FP, str);
        curTemp++;
        body.code(classNode, classTable, curTemp, str);
        classTable.exitScope();
    }

    public int getTempNumber() {
        return Math.max(init.getTempNumber(), body.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'plus'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class plus extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "plus" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new plus(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (e1 instanceof int_const) {
            int_const const1 = (int_const) e1;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        } else {
            e1.code(classNode, classTable, curTemp, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        }

        if (e2 instanceof int_const) {
            int_const const1 = (int_const) e2;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
        } else {
            e2.code(classNode, classTable, curTemp+1, str);
        }

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, str);
        CgenSupport.emitStore(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int_protObj", str);
        CgenSupport.emitJal("Object.copy", str);
        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, str);
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'sub'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class sub extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "sub" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new sub(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (e1 instanceof int_const) {
            int_const const1 = (int_const) e1;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        } else {
            e1.code(classNode, classTable, curTemp, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        }

        if (e2 instanceof int_const) {
            int_const const1 = (int_const) e2;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
        } else {
            e2.code(classNode, classTable, curTemp+1, str);
        }

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitSub(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, str);
        CgenSupport.emitStore(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int_protObj", str);
        CgenSupport.emitJal("Object.copy", str);
        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, str);
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'mul'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class mul extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "mul" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new mul(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (e1 instanceof int_const) {
            int_const const1 = (int_const) e1;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        } else {
            e1.code(classNode, classTable, curTemp, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        }

        if (e2 instanceof int_const) {
            int_const const1 = (int_const) e2;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
        } else {
            e2.code(classNode, classTable, curTemp+1, str);
        }

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitMul(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, str);
        CgenSupport.emitStore(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int_protObj", str);
        CgenSupport.emitJal("Object.copy", str);
        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, str);

    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'divide'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class divide extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "divide" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new divide(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (e1 instanceof int_const) {
            int_const const1 = (int_const) e1;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        } else {
            e1.code(classNode, classTable, curTemp, str);
            CgenSupport.emitStore(CgenSupport.ACC, curTemp , CgenSupport.FP, str);
        }

        if (e2 instanceof int_const) {
            int_const const1 = (int_const) e2;
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + const1.token.index, str);
        } else {
            e2.code(classNode, classTable, curTemp+1, str);
        }

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitDiv(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, str);
        CgenSupport.emitStore(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int_protObj", str);
        CgenSupport.emitJal("Object.copy", str);
        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, str);
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'neg'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class neg extends Expression {
    public Expression e1;

    /**
     * Creates "neg" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new neg(lineNumber, (Expression) e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        e1.code(classNode, classTable, curTemp, str);
        CgenSupport.emitPush(CgenSupport.ACC, str);
        CgenSupport.emitLoadAddress(CgenSupport.ACC, "Int_protObj", str);

        CgenSupport.emitJal("Object.copy", str);
        CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SP, str);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 4, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, str);
        CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, str);
    }

    public int getTempNumber() {
        return e1.getTempNumber();
    }
}

/**
 * Defines AST constructor 'lt'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class lt extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "lt" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new lt(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        e1.code(classNode, classTable, curTemp, str);
        CgenSupport.emitStore(CgenSupport.ACC, curTemp, CgenSupport.FP, str);
        e2.code(classNode, classTable, curTemp + 1, str);

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, str);

        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.T2, str);

        int label = CgenClassTable.getLabelNumber();
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.truebool.codeRef(str);
        str.println();
        CgenSupport.emitBlt(CgenSupport.T1, CgenSupport.T2, label, str);
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.falsebool.codeRef(str);
        str.println();
        CgenSupport.emitLabelDef(label, str);
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }
}

/**
 * Defines AST constructor 'eq'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class eq extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "eq" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new eq(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        

        int label = classTable.getLabelNumber();
        e1.code(classNode, classTable, curTemp, str);
        CgenSupport.emitPush(CgenSupport.ACC, str);

        e2.code(classNode, classTable, curTemp , str);
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, str);
        CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SP, str);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 4, str);

        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), str);
        CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, label, str);
        CgenSupport.emitLoadBool(CgenSupport.A1, new BoolConst(false), str);
        CgenSupport.emitJal("equality_test", str);

        CgenSupport.emitLabelDef(label, str);

        
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);
    }

}

/**
 * Defines AST constructor 'leq'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class leq extends Expression {
    public Expression e1;
    public Expression e2;

    /**
     * Creates "leq" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     * @param a1         initial value for e2
     */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }

    public TreeNode copy() {
        return new leq(lineNumber, (Expression) e1.copy(), (Expression) e2.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n + 2);
        e2.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
        e1.dump_with_types(out, n + 2);
        e2.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        e1.code(classNode, classTable, curTemp, str);
        CgenSupport.emitStore(CgenSupport.ACC, curTemp, CgenSupport.FP, str);
        e2.code(classNode, classTable, curTemp + 1, str);

        CgenSupport.emitLoad(CgenSupport.T1, curTemp, CgenSupport.FP, str);
        CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, str);

        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, str);
        CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.T2, str);

        int label = CgenClassTable.getLabelNumber();
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.truebool.codeRef(str);
        str.println();
        CgenSupport.emitBleq(CgenSupport.T1, CgenSupport.T2, label, str);
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.falsebool.codeRef(str);
        str.println();
        CgenSupport.emitLabelDef(label, str);
    }

    public int getTempNumber() {
        return Math.max(e1.getTempNumber(), e2.getTempNumber() + 1);

    }
}

/**
 * Defines AST constructor 'comp'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class comp extends Expression {
    public Expression e1;

    /**
     * Creates "comp" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new comp(lineNumber, (Expression) e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        e1.code(classNode, classTable, curTemp, str);
        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, str);
        CgenSupport.emitLoadImm(CgenSupport.T2, 1, str);
        int label = CgenClassTable.getLabelNumber();
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.falsebool.codeRef(str);
        str.println();
        CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, label, str);
        CgenSupport.emitPartialLoadAddress(CgenSupport.ACC, str);
        BoolConst.truebool.codeRef(str);
        str.println();
        CgenSupport.emitLabelDef(label, str);
    }

    public int getTempNumber() {
        return e1.getTempNumber();
    }
}

/**
 * Defines AST constructor 'int_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class int_const extends Expression {
    public AbstractSymbol token;

    /**
     * Creates "int_const" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for token
     */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }

    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n + 2, token);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
        dump_AbstractSymbol(out, n + 2, token);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method method is provided to you as
     * an example of code generation.
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        CgenSupport.emitLoadInt(CgenSupport.ACC, (IntSymbol) AbstractTable.inttable.lookup(token.getString()), str);
    }

    public int getTempNumber() {
        return 0;
    }
}

/**
 * Defines AST constructor 'bool_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class bool_const extends Expression {
    public Boolean val;

    /**
     * Creates "bool_const" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for val
     */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }

    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n + 2, val);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
        dump_Boolean(out, n + 2, val);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method method is provided to you as
     * an example of code generation.
     * 
     * @param s the output stream
     */

    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), str);

    }

    public int getTempNumber() {
        return 0;
    }
}

/**
 * Defines AST constructor 'string_const'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class string_const extends Expression {
    public AbstractSymbol token;

    /**
     * Creates "string_const" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for token
     */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }

    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n + 2, token);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, token.getString());
        out.println("\"");
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method method is provided to you as
     * an example of code generation.
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        CgenSupport.emitLoadString(CgenSupport.ACC, (StringSymbol) AbstractTable.stringtable.lookup(token.getString()),
                str);
    }

    public int getTempNumber() {
        return 0;
    }

}

/**
 * Defines AST constructor 'new_'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class new_ extends Expression {
    public AbstractSymbol type_name;

    /**
     * Creates "new_" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for type_name
     */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }

    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n + 2, type_name);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * the implementation for self_type is very ugly(needs to be improved),test almost all classes
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (type_name.str.equals("SELF_TYPE")) {
            int labelNumber=classTable.getLabelNumber();
            int endNumber=classTable.getLabelNumber();
            for(Enumeration e=classTable.getAllClassNode().elements();e.hasMoreElements();)
            {
                CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.SELF, str);
                CgenNode class_node=(CgenNode)e.nextElement();
                int tagNumber=classTable.getTagNumber(class_node.name);
                CgenSupport.emitLoadImm(CgenSupport.T2, tagNumber, str);
                CgenSupport.emitBne(CgenSupport.T1, CgenSupport.T2, labelNumber, str);
                CgenSupport.emitLoadAddress(CgenSupport.ACC, class_node.name.str + CgenSupport.PROTOBJ_SUFFIX, str);
                CgenSupport.emitJal("Object.copy", str);
                CgenSupport.emitJal(class_node.name.str + CgenSupport.CLASSINIT_SUFFIX, str);
                CgenSupport.emitBranch(endNumber, str);
                CgenSupport.emitLabelDef(labelNumber, str);
                labelNumber=classTable.getLabelNumber();
            }
            CgenSupport.emitLabelDef(endNumber, str);
            
        } else {
            CgenSupport.emitLoadAddress(CgenSupport.ACC, type_name.str + CgenSupport.PROTOBJ_SUFFIX, str);
            CgenSupport.emitJal("Object.copy", str);
            CgenSupport.emitJal(type_name.str + CgenSupport.CLASSINIT_SUFFIX, str);
        }
    }

    public int getTempNumber() {
        return 0;
    }

}

/**
 * Defines AST constructor 'isvoid'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class isvoid extends Expression {
    public Expression e1;

    /**
     * Creates "isvoid" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for e1
     */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }

    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression) e1.copy());
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n + 2);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
        e1.dump_with_types(out, n + 2);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        e1.code(classNode, classTable, curTemp, str);

        int label = CgenClassTable.getLabelNumber();
        CgenSupport.emitMove(CgenSupport.T1, CgenSupport.ACC, str);
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), str);
        CgenSupport.emitBeqz(CgenSupport.T1, label, str);
        CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), str);
        CgenSupport.emitLabelDef(label, str);
    }

    public int getTempNumber() {
        return e1.getTempNumber();
    }

}

/**
 * Defines AST constructor 'no_expr'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class no_expr extends Expression {
    /**
     * Creates "no_expr" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }

    public TreeNode copy() {
        return new no_expr(lineNumber);
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        return;
    }

    public int getTempNumber() {
        return 0;
    }

}

/**
 * Defines AST constructor 'object'.
 * <p>
 * See <a href="TreeNode.html">TreeNode</a> for full documentation.
 */
class object extends Expression {
    public AbstractSymbol name;

    /**
     * Creates "object" AST node.
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0         initial value for name
     */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }

    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }

    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n + 2, name);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
        dump_AbstractSymbol(out, n + 2, name);
        dump_type(out, n);
    }

    /**
     * Generates code for this expression. This method is to be completed in
     * programming assignment 5. (You may add or remove parameters as you wish.)
     * 
     * @param s the output stream
     */
    public void code(CgenNode classNode, CgenClassTable classTable, int curTemp, PrintStream str) {
        if (name.str.equals("self")) {
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, str);
            return;
        } else if (classTable.lookup(name) != null) {
            Integer offSet = (Integer) classTable.lookup(name);
            CgenSupport.emitLoad(CgenSupport.ACC, offSet, CgenSupport.FP, str);
            return;
        } else {
            Integer offSet = classTable.getAttrOffset(classNode, name);
            CgenSupport.emitLoad(CgenSupport.ACC, offSet, CgenSupport.SELF, str);
            return;
        }

    }

    public int getTempNumber() {
        return 0;
    }

}
