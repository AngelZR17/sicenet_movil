package com.brian_angel.sicenet.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class AccessLoginResponse @JvmOverloads constructor(
    @field:Element(name = "Body")
    var body: Body? = null
)

@Root(name = "Body")
data class Body @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResponse")
    var accesoLoginResponse: AccesoLoginResponse? = null
)

@Root(name = "accesoLoginResponse")
@Namespace(reference = "http://tempuri.org/")
data class AccesoLoginResponse @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResult")
    var accesoLoginResult: String? = null
)