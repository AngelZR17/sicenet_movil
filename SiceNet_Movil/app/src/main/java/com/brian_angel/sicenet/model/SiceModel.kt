package com.brian_angel.sicenet.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelope @JvmOverloads constructor(
    @field:Element(name = "Body")
    var body: SoapBody? = null
)

@Root(name = "Body")
data class SoapBody @JvmOverloads constructor(
    @field:Element(name = "accesoLogin")
    var accesoLogin: AccesoLogin? = null
)

@Root(name = "accesoLogin")
@Namespace(reference = "http://tempuri.org/")
data class AccesoLogin @JvmOverloads constructor(
    @field:Element(name = "strMatricula")
    var strMatricula: String? = null,

    @field:Element(name = "strContrasenia")
    var strContrasenia: String? = null,

    @field:Element(name = "tipoUsuario")
    var tipoUsuario: String? = null
)
