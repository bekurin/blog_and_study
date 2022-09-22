package com.example.core.c_classObjectInterface

import javax.naming.Context
import javax.print.attribute.AttributeSet

open class MainConstructor(val name: String, var type: String) {

}

open class View {
    constructor(ctx: Context) {

    }

    constructor(ctx: Context, attr: String) {

    }
}

class MyButton: View {

    constructor(ctx: Context): this(ctx, "MY_STYLE") {
        // ctx, attr이 포함된 생성자에 권한 위임
    }

    constructor(ctx: Context, attr: String): super(ctx, attr) {

    }
}