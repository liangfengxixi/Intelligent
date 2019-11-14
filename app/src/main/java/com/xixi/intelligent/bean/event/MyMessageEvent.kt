package com.xixi.intelligent.bean.event


class MyMessageEvent {
    var type: String
    var passValue: Any ? =null
    var passValue2: Any ? =null

    constructor(type: String, passValue: Any?) {
        this.type = type
        this.passValue = passValue
    }

    constructor(type: String, passValue: Any, passValue2: Any?) {
        this.type = type
        this.passValue = passValue
        this.passValue2 = passValue2
    }
}
