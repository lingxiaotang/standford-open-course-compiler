/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.lang.System.Logger;
import java.util.Vector;

import javax.naming.ldap.HasControls;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * This class is used for representing the inheritance tree during code
 * generation. You will need to fill in some of its methods and potentially
 * extend it in other useful ways.
 */
class CgenClassTable extends SymbolTable {

    /** All classes in the program, represented as CgenNode */
    private Vector nds;

    /** This is the stream to which assembly instructions are output */
    private PrintStream str;

    private int stringclasstag;
    private int intclasstag;
    private int boolclasstag;
    private int objectclasstag;
    private int ioclasstag;
    private HashMap<Integer, AbstractSymbol> tagNumberToClassName = new HashMap<Integer, AbstractSymbol>();
    private HashMap<AbstractSymbol, Integer> classNameToTagNumber = new HashMap<AbstractSymbol, Integer>();
    private HashMap<AbstractSymbol, CgenNode> symbolToNode = new HashMap<AbstractSymbol, CgenNode>();

    // The following methods emit code for constants and global
    // declarations.

    /**
     * Emits code to start the .data segment and to declare the global names.please
     * see spim manual(page 51) for more detail.
     */
    private void codeGlobalData() {
        // The following global names must be defined first.

        str.print("\t.data\n" + CgenSupport.ALIGN);
        str.println(CgenSupport.GLOBAL + CgenSupport.CLASSNAMETAB);
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Main, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Int, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitProtObjRef(TreeConstants.Str, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        BoolConst.falsebool.codeRef(str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        BoolConst.truebool.codeRef(str);
        str.println("");
        str.println(CgenSupport.GLOBAL + CgenSupport.INTTAG);
        str.println(CgenSupport.GLOBAL + CgenSupport.BOOLTAG);
        str.println(CgenSupport.GLOBAL + CgenSupport.STRINGTAG);

        // We also need to know the tag of the Int, String, and Bool classes
        // during code generation.

        str.println(CgenSupport.INTTAG + CgenSupport.LABEL + CgenSupport.WORD + intclasstag);
        str.println(CgenSupport.BOOLTAG + CgenSupport.LABEL + CgenSupport.WORD + boolclasstag);
        str.println(CgenSupport.STRINGTAG + CgenSupport.LABEL + CgenSupport.WORD + stringclasstag);

    }

    /**
     * Emits code to start the .text segment and to declare the global names.
     */
    private void codeGlobalText() {
        str.println(CgenSupport.GLOBAL + CgenSupport.HEAP_START);
        str.print(CgenSupport.HEAP_START + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + 0);
        str.println("\t.text");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Main, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Int, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Str, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitInitRef(TreeConstants.Bool, str);
        str.println("");
        str.print(CgenSupport.GLOBAL);
        CgenSupport.emitMethodRef(TreeConstants.Main, TreeConstants.main_meth, str);
        str.println("");
    }

    /** Emits code definitions for boolean constants. */
    private void codeBools(int classtag) {
        BoolConst.falsebool.codeDef(classtag, str);
        BoolConst.truebool.codeDef(classtag, str);
    }

    /** Generates GC choice constants (pointers to GC functions) */
    private void codeSelectGc() {
        str.println(CgenSupport.GLOBAL + "_MemMgr_INITIALIZER");
        str.println("_MemMgr_INITIALIZER:");
        str.println(CgenSupport.WORD + CgenSupport.gcInitNames[Flags.cgen_Memmgr]);

        str.println(CgenSupport.GLOBAL + "_MemMgr_COLLECTOR");
        str.println("_MemMgr_COLLECTOR:");
        str.println(CgenSupport.WORD + CgenSupport.gcCollectNames[Flags.cgen_Memmgr]);

        str.println(CgenSupport.GLOBAL + "_MemMgr_TEST");
        str.println("_MemMgr_TEST:");
        str.println(CgenSupport.WORD + ((Flags.cgen_Memmgr_Test == Flags.GC_TEST) ? "1" : "0"));
    }

    /**
     * Emits code to reserve space for and initialize all of the constants. Class
     * names should have been added to the string table (in the supplied code, is is
     * done during the construction of the inheritance graph), and code for emitting
     * string constants as a side effect adds the string's length to the integer
     * table. The constants are emmitted by running through the stringtable and
     * inttable and producing code for each entry.
     */
    private void codeConstants() {
        // Add constants that are required by the code generator.
        AbstractTable.stringtable.addString("");
        AbstractTable.inttable.addString("0");

        defaultInt = CgenSupport.INTCONST_PREFIX + AbstractTable.inttable.lookup("0").index;
        defaultString = CgenSupport.STRCONST_PREFIX + AbstractTable.stringtable.lookup("").index;

        AbstractTable.stringtable.codeStringTable(stringclasstag, str);
        AbstractTable.inttable.codeStringTable(intclasstag, str);

        codeBools(boolclasstag);
    }

    /**
     * Creates data structures representing basic Cool classes (Object, IO, Int,
     * Bool, String). Please note: as is this method does not do anything useful;
     * you will need to edit it to make if do what you want.
     */
    private void installBasicClasses() {
        AbstractSymbol filename = AbstractTable.stringtable.addString("<basic class>");

        // A few special class names are installed in the lookup table
        // but not the class list. Thus, these classes exist, but are
        // not part of the inheritance hierarchy. No_class serves as
        // the parent of Object and the other special classes.
        // SELF_TYPE is the self class; it cannot be redefined or
        // inherited. prim_slot is a class known to the code generator.

        addId(TreeConstants.No_class,
                new CgenNode(new class_(0, TreeConstants.No_class, TreeConstants.No_class, new Features(0), filename),
                        CgenNode.Basic, this));

        addId(TreeConstants.SELF_TYPE,
                new CgenNode(new class_(0, TreeConstants.SELF_TYPE, TreeConstants.No_class, new Features(0), filename),
                        CgenNode.Basic, this));

        addId(TreeConstants.prim_slot,
                new CgenNode(new class_(0, TreeConstants.prim_slot, TreeConstants.No_class, new Features(0), filename),
                        CgenNode.Basic, this));

        // The Object class has no parent class. Its methods are
        // cool_abort() : Object aborts the program
        // type_name() : Str returns a string representation
        // of class name
        // copy() : SELF_TYPE returns a copy of the object

        class_ Object_class = new class_(0, TreeConstants.Object_, TreeConstants.No_class, new Features(0)
                .appendElement(
                        new method(0, TreeConstants.cool_abort, new Formals(0), TreeConstants.Object_, new no_expr(0)))
                .appendElement(
                        new method(0, TreeConstants.type_name, new Formals(0), TreeConstants.Str, new no_expr(0)))
                .appendElement(
                        new method(0, TreeConstants.copy, new Formals(0), TreeConstants.SELF_TYPE, new no_expr(0))),
                filename);

        CgenNode ObjectNode = new CgenNode(Object_class, CgenNode.Basic, this);
        installClass(ObjectNode);
        symbolToNode.put(ObjectNode.name, ObjectNode);
        tagNumberToClassName.put(objectclasstag, Object_class.name);
        classNameToTagNumber.put(Object_class.name, objectclasstag);
        // The IO class inherits from Object. Its methods are
        // out_string(Str) : SELF_TYPE writes a string to the output
        // out_int(Int) : SELF_TYPE " an int " " "
        // in_string() : Str reads a string from the input
        // in_int() : Int " an int " " "

        class_ IO_class = new class_(0, TreeConstants.IO, TreeConstants.Object_,
                new Features(0)
                        .appendElement(new method(0, TreeConstants.out_string,
                                new Formals(0).appendElement(new formal(0, TreeConstants.arg, TreeConstants.Str)),
                                TreeConstants.SELF_TYPE, new no_expr(0)))
                        .appendElement(new method(0, TreeConstants.out_int,
                                new Formals(0).appendElement(new formal(0, TreeConstants.arg, TreeConstants.Int)),
                                TreeConstants.SELF_TYPE, new no_expr(0)))
                        .appendElement(new method(0, TreeConstants.in_string, new Formals(0), TreeConstants.Str,
                                new no_expr(0)))
                        .appendElement(
                                new method(0, TreeConstants.in_int, new Formals(0), TreeConstants.Int, new no_expr(0))),
                filename);

        CgenNode IONode = new CgenNode(IO_class, CgenNode.Basic, this);
        installClass(IONode);
        symbolToNode.put(IO_class.name, IONode);
        tagNumberToClassName.put(ioclasstag, IO_class.name);
        classNameToTagNumber.put(IO_class.name, ioclasstag);
        // The Int class has no methods and only a single attribute, the
        // "val" for the integer.

        class_ Int_class = new class_(0, TreeConstants.Int, TreeConstants.Object_,
                new Features(0).appendElement(new attr(0, TreeConstants.val, TreeConstants.prim_slot, new no_expr(0))),
                filename);

        CgenNode IntNode = new CgenNode(Int_class, CgenNode.Basic, this);
        installClass(IntNode);
        symbolToNode.put(Int_class.name, IntNode);
        tagNumberToClassName.put(intclasstag, Int_class.name);
        classNameToTagNumber.put(Int_class.name, intclasstag);
        // Bool also has only the "val" slot.
        class_ Bool_class = new class_(0, TreeConstants.Bool, TreeConstants.Object_,
                new Features(0).appendElement(new attr(0, TreeConstants.val, TreeConstants.prim_slot, new no_expr(0))),
                filename);

        CgenNode BoolNode = new CgenNode(Bool_class, CgenNode.Basic, this);
        installClass(BoolNode);
        symbolToNode.put(Bool_class.name, BoolNode);
        tagNumberToClassName.put(boolclasstag, Bool_class.name);
        classNameToTagNumber.put(Bool_class.name, boolclasstag);
        // The class Str has a number of slots and operations:
        // val the length of the string
        // str_field the string itself
        // length() : Int returns length of the string
        // concat(arg: Str) : Str performs string concatenation
        // substr(arg: Int, arg2: Int): Str substring selection

        class_ Str_class = new class_(0, TreeConstants.Str, TreeConstants.Object_, new Features(0)
                .appendElement(new attr(0, TreeConstants.val, TreeConstants.Int, new no_expr(0)))
                .appendElement(new attr(0, TreeConstants.str_field, TreeConstants.prim_slot, new no_expr(0)))
                .appendElement(new method(0, TreeConstants.length, new Formals(0), TreeConstants.Int, new no_expr(0)))
                .appendElement(new method(0, TreeConstants.concat,
                        new Formals(0).appendElement(new formal(0, TreeConstants.arg, TreeConstants.Str)),
                        TreeConstants.Str, new no_expr(0)))
                .appendElement(new method(0, TreeConstants.substr,
                        new Formals(0).appendElement(new formal(0, TreeConstants.arg, TreeConstants.Int))
                                .appendElement(new formal(0, TreeConstants.arg2, TreeConstants.Int)),
                        TreeConstants.Str, new no_expr(0))),
                filename);

        CgenNode StrNode = new CgenNode(Str_class, CgenNode.Basic, this);
        installClass(StrNode);
        symbolToNode.put(Str_class.name, StrNode);
        tagNumberToClassName.put(stringclasstag, Str_class.name);
        classNameToTagNumber.put(Str_class.name, stringclasstag);
    }

    // The following creates an inheritance graph from
    // a list of classes. The graph is implemented as
    // a tree of `CgenNode', and class names are placed
    // in the base class symbol table.

    private void installClass(CgenNode nd) {
        AbstractSymbol name = nd.getName();
        if (probe(name) != null)
            return;
        nds.addElement(nd);
        addId(name, nd);
    }

    private void installClasses(Classes cs) {
        for (Enumeration e = cs.getElements(); e.hasMoreElements();) {
            installClass(new CgenNode((Class_) e.nextElement(), CgenNode.NotBasic, this));
        }
    }

    private void buildInheritanceTree() {
        for (Enumeration e = nds.elements(); e.hasMoreElements();) {
            setRelations((CgenNode) e.nextElement());
        }
    }

    private void setRelations(CgenNode nd) {
        CgenNode parent = (CgenNode) probe(nd.getParent());
        nd.setParentNd(parent);
        parent.addChild(nd);
    }

    /** Constructs a new class table and invokes the code generator */
    public CgenClassTable(Classes cls, PrintStream str) {
        nds = new Vector();

        this.str = str;

        stringclasstag = 4 /* Change to your String class tag here */;
        intclasstag = 2 /* Change to your Int class tag here */;
        boolclasstag = 3 /* Change to your Bool class tag here */;
        objectclasstag = 0;
        ioclasstag = 1;

        enterScope();
        if (Flags.cgen_debug)
            System.out.println("Building CgenClassTable");

        installBasicClasses();
        installClasses(cls);
        buildInheritanceTree();

        code();

        exitScope();
    }

    /** Gets the root of the inheritance tree */
    public CgenNode root() {
        return (CgenNode) probe(TreeConstants.Object_);
    }

    /**
     * This method is the meat of the code generator. It is to be filled in
     * programming assignment 5
     */
    public void code() {
        if (Flags.cgen_debug)
            System.out.println("coding global data");
        codeGlobalData();

        if (Flags.cgen_debug)
            System.out.println("choosing gc");
        codeSelectGc();

        if (Flags.cgen_debug)
            System.out.println("coding constants");
        codeConstants();

        // Add your code to emit
        // - prototype objects
        // - class_nameTab
        // - dispatch tables
        emitClassNameTab();
        emitClassObj();
        emitDispatchTable();
        emitObjectPrototype();

        if (Flags.cgen_debug)
            System.out.println("coding global text");
        codeGlobalText();

        // Add your code to emit
        // - object initializer
        // - the class methods
        // - etc...

        emitObjectInit();
        emitObjectMethod();
    }

    private static HashMap<AbstractSymbol, List<AbstractSymbol>> classMethodTable = new HashMap<AbstractSymbol, List<AbstractSymbol>>();

    private static int labelNumber = 0;

    public static int getLabelNumber() {
        return labelNumber++;
    }

    void emitClassNameTab() {
        str.print(CgenSupport.CLASSNAMETAB + CgenSupport.LABEL);
        for (Object cgenNode : nds) {
            CgenNode class_node = (CgenNode) cgenNode;
            int index = AbstractTable.stringtable.lookup(class_node.name.str).index;
            str.println(CgenSupport.WORD + CgenSupport.STRCONST_PREFIX + index);
        }
    }

    void emitClassObj() {
        str.print(CgenSupport.CLASSOBJTAB + CgenSupport.LABEL);
        for (Object cgenNode : nds) {
            CgenNode class_node = (CgenNode) cgenNode;
            str.println(CgenSupport.WORD + class_node.name.str + CgenSupport.PROTOBJ_SUFFIX);
            str.println(CgenSupport.WORD + class_node.name.str + CgenSupport.CLASSINIT_SUFFIX);
        }
    }

    void emitDispatchTable() {
        for (Object classNode : nds) {
            str.print(((CgenNode) classNode).name.str + CgenSupport.DISPTAB_SUFFIX + CgenSupport.LABEL);
            List<AbstractSymbol> methods = new ArrayList<>();
            HashMap<AbstractSymbol, AbstractSymbol> methodMap = new HashMap<AbstractSymbol, AbstractSymbol>();
            buildFilterMap(methodMap, (CgenNode) classNode);
            emitDispatchTable(methodMap, (CgenNode) classNode, methods);
            classMethodTable.put(((CgenNode) classNode).name, methods);
        }
    }

    public static String defaultInt;
    public static String defaultString;
    private static int classTag = 5;

    public int getTagNumber(AbstractSymbol TypeName) {
        return classNameToTagNumber.get(TypeName);
    }

    public AbstractSymbol getClassName(int tagNumber) {
        return tagNumberToClassName.get(tagNumber);
    }

    public CgenNode getNode(AbstractSymbol className) {
        return symbolToNode.get(className);
    }

    /**
     * return true if type1 is a subtype of type2
     * 
     * @param type1
     * @param type2
     * @return
     */
    public boolean isSubtype(AbstractSymbol type1, AbstractSymbol type2) {

        CgenNode node1 = getNode(type1);
        if (type2.str.equals("Object"))
            return true;
        else if (type1.str.equals(type2.str))
            return true;
        else {
            while (!node1.name.str.equals("Object")) {
                if (node1.name.str.equals(type2.str))
                    return true;
                node1 = node1.getParentNd();
            }
        }
        return false;
    }

    /**
     * 
     * @param type
     * @return the lowest subtype of the input type(hence the largest tag one)
     */
    public int getLowestSubtypeTag(AbstractSymbol type) {
        int maxTag = -1;
        for (Object classObject : nds) {
            CgenNode classNode = (CgenNode) classObject;
            AbstractSymbol classType = classNode.name;
            if (isSubtype(classType, type)) {
                int thisTagNumber = getTagNumber(classType);
                if (thisTagNumber > maxTag)
                    maxTag = thisTagNumber;
            }
        }

        return maxTag;
    }

    /**
     * 
     * @return a new class tag for a new defined class
     */
    private static int getClassTag() {
        return classTag++;
    }

    /**
     * emit all object prototypes
     */
    void emitObjectPrototype() {
        emitBasicObjectPrototye();
        CgenNode rootClass = root();
        emitemitObjectPrototype(rootClass);

    }

    /**
     * @return return all attributes of a CgenNode
     */
    List<attr> getAllAttributes(CgenNode classNode) {
        List<CgenNode> inheritTree = buildInheritTree(classNode);
        List<attr> attributes = new ArrayList<>();
        for (int i = 0; i < inheritTree.size(); i++) {
            for (Enumeration e = inheritTree.get(i).getFeatures().getElements(); e.hasMoreElements();) {
                Feature feature = (Feature) e.nextElement();
                if (feature instanceof attr) {
                    attributes.add((attr) feature);
                }
            }
        }
        return attributes;
    }

    void emitemitObjectPrototype(CgenNode classNode) {

        if (!classNode.basic()) {
            str.println(CgenSupport.WORD + "-1");
            int classTag = getClassTag();
            tagNumberToClassName.put(new Integer(classTag), classNode.name);
            classNameToTagNumber.put(classNode.name, new Integer(classTag));
            List<attr> attributes = getAllAttributes(classNode);
            int attrNumber = attributes.size();
            tagNumberToClassName.put(classTag, classNode.name);
            classNameToTagNumber.put(classNode.name, classTag);
            symbolToNode.put(classNode.name, classNode);

            str.print(classNode.name.str + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
            str.println(CgenSupport.WORD + classTag);
            int size = 3 + attrNumber;
            str.println(CgenSupport.WORD + size);
            str.println(CgenSupport.WORD + classNode.name.str + CgenSupport.DISPTAB_SUFFIX);
            for (int i = 0; i < attributes.size(); i++) {
                attr attribute = attributes.get(i);
                if (attribute.type_decl.str.equals("String"))
                    str.println(CgenSupport.WORD + defaultString);
                else if (attribute.type_decl.str.equals("Int"))
                    str.println(CgenSupport.WORD + defaultInt);
                else
                    str.println(CgenSupport.WORD + "0");
            }

            for (Enumeration e = classNode.getChildren(); e.hasMoreElements();) {
                CgenNode childNode = (CgenNode) e.nextElement();
                emitemitObjectPrototype(childNode);
            }
        }
        if (classNode.basic()) {
            for (Enumeration e = classNode.getChildren(); e.hasMoreElements();) {
                CgenNode childNode = (CgenNode) e.nextElement();
                emitemitObjectPrototype(childNode);
            }
        }
    }

    void emitBasicObjectPrototye() {
        emitObjectprototype();
        emitIOprototype();
        emitStringprototype();
        emitIntprototype();
        emitBoolprototype();
    }

    void emitObjectprototype() {
        str.println(CgenSupport.WORD + "-1");
        str.print("Object" + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + objectclasstag);
        str.println(CgenSupport.WORD + 3);
        str.println(CgenSupport.WORD + "Object" + CgenSupport.DISPTAB_SUFFIX);
    }

    void emitIOprototype() {
        str.println(CgenSupport.WORD + "-1");
        str.print("IO" + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + ioclasstag);
        str.println(CgenSupport.WORD + 3);
        str.println(CgenSupport.WORD + "IO" + CgenSupport.DISPTAB_SUFFIX);
    }

    void emitStringprototype() {
        str.println(CgenSupport.WORD + "-1");
        str.print("String" + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + stringclasstag);
        str.println(CgenSupport.WORD + 5);
        str.println(CgenSupport.WORD + "String" + CgenSupport.DISPTAB_SUFFIX);
        str.println(CgenSupport.WORD + defaultInt);
        str.println(CgenSupport.WORD + "0");
    }

    void emitIntprototype() {
        str.println(CgenSupport.WORD + "-1");
        str.print("Int" + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + intclasstag);
        str.println(CgenSupport.WORD + 4);
        str.println(CgenSupport.WORD + "Int" + CgenSupport.DISPTAB_SUFFIX);
        str.println(CgenSupport.WORD + "0");
    }

    void emitBoolprototype() {
        str.println(CgenSupport.WORD + "-1");
        str.print("Bool" + CgenSupport.PROTOBJ_SUFFIX + CgenSupport.LABEL);
        str.println(CgenSupport.WORD + boolclasstag);
        str.println(CgenSupport.WORD + 4);
        str.println(CgenSupport.WORD + "Bool" + CgenSupport.DISPTAB_SUFFIX);
        str.println(CgenSupport.WORD + "0");
    }

    void buildFilterMap(HashMap<AbstractSymbol, AbstractSymbol> methodMap, CgenNode class_node) {
        if (class_node.getName().str.equals("Object")) {
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature classFeature = (Feature) e.nextElement();
                if (classFeature instanceof method) {
                    method classMethod = (method) classFeature;
                    methodMap.put(classMethod.name, class_node.name);
                }
            }
            return;
        } else {
            buildFilterMap(methodMap, class_node.getParentNd());
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature classFeature = (Feature) e.nextElement();
                if (classFeature instanceof method) {
                    method classMethod = (method) classFeature;
                    methodMap.put(classMethod.name, class_node.name);
                }
            }
        }
    }

    /**
     * 
     * @param methodMap
     * @param class_node
     * @param methods
     */
    void emitDispatchTable(HashMap<AbstractSymbol, AbstractSymbol> methodMap, CgenNode class_node,
            List<AbstractSymbol> methods) {
        if (class_node.getName().str.equals("Object")) {
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature classFeature = (Feature) e.nextElement();
                if (classFeature instanceof method) {
                    method classMethod = (method) classFeature;
                    if (methodMap.containsKey(classMethod.name)) {
                        AbstractSymbol overridenClassName = methodMap.get(classMethod.name);
                        str.println(
                                CgenSupport.WORD + overridenClassName + CgenSupport.METHOD_SEP + classMethod.name.str);
                        methods.add(classMethod.name);
                        methodMap.remove(classMethod.name);
                    }
                }
            }
        } else {
            emitDispatchTable(methodMap, class_node.getParentNd(), methods);
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature classFeature = (Feature) e.nextElement();
                if (classFeature instanceof method) {
                    method classMethod = (method) classFeature;
                    if (methodMap.containsKey(classMethod.name)) {
                        AbstractSymbol overridenClassName = methodMap.get(classMethod.name);
                        
                            str.println(CgenSupport.WORD + overridenClassName + CgenSupport.METHOD_SEP
                                    + classMethod.name.str);
                            methods.add(classMethod.name);
                            methodMap.remove(classMethod.name);
                        
                    }
                }
            }
        }
    }

    public static int getMethodOffset(CgenNode classNode, AbstractSymbol methodName) {
        List<CgenNode> classList = buildInheritTree(classNode);
        int offSet = 0;
        for (var class_node : classList) {
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature feature = (Feature) e.nextElement();
                if (feature instanceof method) {
                    method m = (method) feature;
                    if (m.name.str.equals(methodName.str))
                        return offSet;
                    else
                        offSet++;
                }
            }
        }
        return -1;
    }

    public static int getAttrOffset(CgenNode classNode, AbstractSymbol attrName) {
        List<CgenNode> classList = buildInheritTree(classNode);
        int offSet = 0;
        for (var class_node : classList) {
            for (Enumeration e = class_node.features.getElements(); e.hasMoreElements();) {
                Feature feature = (Feature) e.nextElement();
                if (feature instanceof attr) {
                    attr attribute = (attr) feature;
                    if (attribute.name.str.equals(attrName.str))
                        return offSet + CgenSupport.DEFAULT_OBJFIELDS;
                    else
                        offSet++;
                }
            }
        }
        return -1;
    }

    static List<CgenNode> buildInheritTree(CgenNode classNode) {
        Stack<CgenNode> classStack = new Stack<CgenNode>();
        List<CgenNode> classList = new ArrayList<CgenNode>();
        while (!classNode.name.str.equals("Object")) {
            classStack.push(classNode);
            classNode = classNode.getParentNd();
        }
        classStack.push(classNode);

        while (!classStack.isEmpty())
            classList.add(classStack.pop());

        return classList;
    }

    void emitObjectInit() {
        for (Object class_node : nds) {
            emitObjectInit((CgenNode) class_node);
        }
    }

    void emitObjectInit(CgenNode classNode) {
        int tempNumber = getAllAttrTempNumber(classNode);
        str.print(classNode.name.str + CgenSupport.CLASSINIT_SUFFIX + CgenSupport.LABEL);
        CgenSupport.emitEnterFunction(tempNumber, str);

        if (!classNode.name.str.equals("Object")) {
            CgenSupport.emitJal(classNode.getParentNd().name.str + CgenSupport.CLASSINIT_SUFFIX, str);
        }

        for (Enumeration e = classNode.features.getElements(); e.hasMoreElements();) {
            Feature feature = (Feature) e.nextElement();
            if (feature instanceof attr) {
                attr attribute = (attr) feature;
                attribute.code(classNode, this, str);
            }
        }

        CgenSupport.emitInitExitFunction(0, tempNumber, str);
    }

    int getAllAttrTempNumber(CgenNode classNode) {
        int resultNumber = 0;
        for (Enumeration e = classNode.features.getElements(); e.hasMoreElements();) {
            Feature feature = (Feature) e.nextElement();
            if (feature instanceof attr) {
                attr attribute = (attr) feature;
                if (attribute.getTempNumber() > resultNumber)
                    resultNumber = attribute.getTempNumber();
            }
        }
        return resultNumber;
    }

    void emitObjectMethod() {
        for (Object class_node : nds) {
            CgenNode classNode = (CgenNode) class_node;
            if (!classNode.basic())
                emitObjectMethod(classNode);
        }
    }

    void emitObjectMethod(CgenNode classNode) {
        for (Enumeration e = classNode.features.getElements(); e.hasMoreElements();) {
            Feature f = (Feature) e.nextElement();
            if (f instanceof method) {
                method m = (method) f;
                str.print(classNode.name.str + CgenSupport.METHOD_SEP + m.name.str + CgenSupport.LABEL);
                m.code(classNode, this, str);
            }
        }
    }


    /**
     * 
     * @return all cgen class nodes
     */
    public Vector getAllClassNode(){
        return nds;
    }

}
