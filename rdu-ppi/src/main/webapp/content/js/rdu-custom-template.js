/*
 * Autor: ISC. Carlos Francisco Rivera Zepeda.
 * INFOTEC.
 * Proyecto: RDU
 * 25 de Agosto de 2011.
 *
 * Esto archivo tiene el objetivo de registrar todos los funciones-componentes JavaScript-JQuery que se desarrollen y que son
 * utilizados en todo el contexto del proyecto.
 *
 **/
/**
 * showHideSitioWebNorma()
 * Oculta y desoculta elementos html. Opera sobre los IDs de los elementos, ej:
 *  ID: slcIdTipoEnlace.
 *  $("#calFechaInicio").hide(); ==> Oculta el elemento "calFechaInicio".
 *
 *
 ***/
$ = jQuery;
$(function() {

    //El calendario
    $(".calendar").click(function() {
        $(".ui-datepicker-trigger").click();
    });
    setLocate();
});

function hideInputext() {
    $('#paisSelect').hide();
}

function setLocate()
{
    PrimeFaces.locales['es'] = {
        closeText: 'Cerrar',
        prevText: 'Anterior',
        nextText: 'Siguiente',
        currentText: 'Inicio',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
        dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Sólo hora',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        ampm: false,
        month: 'Mes',
        week: 'Semana',
        day: 'Día',
        allDayText: 'Todo el día'
    };
}

/** Funcion de pruebas: Es utilizada por el desarrollador (en turno) para realizar pruebas en cualquier evento.
 *   El contenido es modificable, se recomienda hacer un respaldo del c�digo previo a la modificaci�n. */
function ejecutarPruebas() {

    var idHdIdTipoEnlace = $("#idHdIdTipoEnlace").val();
    alert("ID HIDDEN TIPO DE ENLACE: " + idHdIdTipoEnlace)

}


/** Aparece/Desaparecer la opci�n "Otra Dependencia" en el registro de usuarios Internos */
function mostrarOtra()
{
    // alert("si entra")
    otraDependencia = $("#form:slcORDep_input").val();

    if (otraDependencia == 0) {
        $("#newdependencia").show();
        $("#otxtOtraDependencia").show();

    } else {
        $("#newdependencia").hide();
        $("#otxtOtraDependencia").hide();
    }
}


/** Mostrar en un iFrame un hipervinculo capturado */
function validarHipervinculo() {

    url = $("#txtHipervinculo").val();

    $("iframe").attr({
        src: url
    });
}

/**
 *tes para ocultar componentes
 */
function showHideTest() {
    tipoArea = $("#slcIdTstTipoArea").val();
    alert("entra:" + tipoArea);
    if (tipoArea == 1) { // marcas
        $("#outPnlTstPatentes").hide('slide', {
            direction: "up"
        }, 800);

        $("#outPnlTstMarcas").show('slide', {
            direction: "up"
        }, 800);

    }
    if (tipoArea == 2) { // patentes
        $("#outPnlTstMarcas").hide('slide', {
            direction: "up"
        }, 800);

        $("#outPnlTstPatentes").show('slide', {
            direction: "up"
        }, 800);

    }


}

function showHideMarcas() {
    tipomarca = $("#tipomarcaSelect").val();
    alert("entra:" + tipomarca);
    //if(tipomarca==2){ // marcas
    ///        $("#panelLeyendas").hide('slide', {
    //          direction: "up"
    //    }, 800);

    $("#outPnlTstMarcas").show('slide', {
        direction: "up"
    }, 800);

//}


}





/** Aparecer/Desaparecer elementos de la captura de Enlaces */
function hideSelect()
{
    alert('adsfdgfhjkjl');
    $('paisSelect').hide();
}


function redirecConsultaFiltrosReporteMB() {
    pagina = $("#consultasForm\\:idHdnPagina").val();
    //alert(pagina);
    window.open(pagina, "_newtab");
}

function upperCase(element) {
    var oldValue = element.value;
    var newValue = oldValue.toUpperCase();
    element.value = newValue;
}

function handleRequest(xhr, status, args) {
    if (args.ok) {
        alert('Informe generado correctamente');
    } else {
        alert('Ha ocurrido un error en la generación del informe');
    }
}


function submitFirmForm(cr, issuer, certificadora, impi, response, fechaOcsp, cadenaEncode, fechaExpiracion, publicKey, serialNumber) {


    document.getElementById("formaFirma:certId").value = cr;
    document.getElementById("formaFirma:firmanteNombreId").value = issuer;
    document.getElementById("formaFirma:firmanteEncodeId").value = cadenaEncode;
    document.getElementById("formaFirma:certificadoraId").value = certificadora;
    document.getElementById("formaFirma:firmaDigitalPromoventeId").value = cr;
    document.getElementById("formaFirma:firmaImpiId").value = impi;
    document.getElementById("formaFirma:ocspResponseId").value = response;
    document.getElementById("formaFirma:fechaOcspId").value = fechaOcsp;
    document.getElementById("formaFirma:fechaExpiracionId").value = fechaExpiracion;
    document.getElementById("formaFirma:publicKeyId").value = publicKey;
    document.getElementById("formaFirma:firmanteBaseId").value = serialNumber;


    var submitThis = document.getElementById("formaFirma:hideSubmit");
    if (document.createEvent) {
        var evObj = document.createEvent('MouseEvents')
        evObj.initEvent('click', true, false);
        // $('#formaFirma\\:modalDialogFirm').hide();

        dlgFirm.hide();
        jQuery('#modalDialogButton').fadeOut();


        $('#formaFirma\\:modalDialogFirm_modal').hide();

        submitThis.dispatchEvent(evObj);

    } else if (document.createEventObject) {
        submitThis.fireEvent('onclick');
    }
}

//Funcion para convertir en mayusculas
/*
 function toUpperCase(field) {
 field.value = field.value.toUpperCase()
 }
 */
function toUpperCase(field)
{
    var cursor = -1;
    var tb = field;
    if (tb != undefined) {
        cursor = tb.selectionStart;
        tb.value = tb.value.toUpperCase();
        /*
         // IE
         if (document.selection && (document.selection != 'undefined'))
         {
         var _range = document.selection.createRange();
         var contador = 0;
         while (_range.move('character', -1))
         contador++;
         cursor = contador;
         }
         // FF
         else if (tb.selectionStart >= 0)
         cursor = tb.selectionStart;
         */
        //tb.selectionStart=cursor;
        tb.setSelectionRange(cursor, cursor);
    }
}

function toUpperCase(e, field) {
    tecla = (document.all) ? e.keyCode : e.which;

    //teclas(flechas de navegacion, inicio, fin y shif)
    if (e.which >= 60 && e.which <= 90 || e.which >= 96 && e.which <= 105 || e.which >= 48 && e.which <= 57) {
        var cursor = -1;
        var tb = field;
        if (tb != undefined) {
            cursor = tb.selectionStart;
            tb.value = tb.value.toUpperCase();
            tb.setSelectionRange(cursor, cursor);
        }
    }
}

function replaceSaltos(field) {
    field.value = field.value.replace(/\s+/g, ' ');
}

function disableCharacters(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    //teclas(flechas de navegacion, inicio, fin y shif)
    if (tecla >= 48 && tecla <= 57 || tecla == 8 || tecla == 0) {
        return true;
    } else {
        return false;
    }
}

function disableEnterKey(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    //teclas(flechas de navegacion, inicio, fin y shif)
    if (tecla != 13) {
        return true;
    } else {
        return false;
    }
}

function enviarConEnter(e, btnId, wVarBtn) {
    tecla = (document.all) ? e.keyCode : e.which;
    boton = document.getElementById('tabs:' + btnId);
    if (tecla != 13) {
        return true;
    } else {
        if (boton.disabled == false) {
//            boton.click(); 
            wVarBtn.jq.click()
//            return false;
        }
        else {
            return false;
        }
        return false;
    }
}

function esNumerico(field) {
    return typeof field.value === 'number' || !isNaN(Number(field.value.replace(/^\s*$/, 'a')));
}

function disableBuscarCP(field, wVarBtn) {
    field.value = field.value.replace(/\D+/g, '');
    if (esNumerico(field) && field.value.length == 5) {
        wVarBtn.enable();
    }
    else {
        wVarBtn.disable();
    }
}

function showValue(field) {
    alert(field.value);
}

/*Funcion para ocultar tabs ejemplo para ocultar la pestaña clase
 <p:tabview id="tabViewerSignosDisitintivos">
 ...
 <p:tab id="tabClase">
 ..
 </p:tab>
 </p:tabview>
 
 <p:commandButton value="hide" onclick="hideTabs('#tabViewerSignosDisitintivos:tabClase')"/>
 */
function hideTabs(id) {
    $('a[href="' + id + '"]').hide();
}

function showTabs(id) {
    $('a[href="' + id + '"]').show();
}

function deshabilitarDivs(nombre) {
    document.getElementById(nombre).style.display = 'none';
}

/*
 *Autor cesar castañeda reyes
 *Funcion para mostrar un mensaje por un tiempo
 *determinado
 **/

function hideMessage(id, limit) {
    var timeOut = 0;
    var interval;
    interval = self.setInterval(function() {
        timer(id, limit)
    }, 1000);
    var div = document.getElementById(id);
    div.style.display = '';

    function timer(id, limit) {
        timeOut++;
        if (timeOut > limit) {
            var div = document.getElementById(id);
            div.style.display = 'none';
            clearInterval(interval);
            timeOut = 0;
        }
    }
}


function hideDialogModal(id) {
    $("#" + id + "_modal").hide();
}
function showStatus(id) {
    var div = document.getElementById(id);
    //var ancho=screen.width/2;
    //var alto=screen.height;
    //div.style.right=ancho+'px';
    div.style.display = 'block';
}
function hideStatus(id) {
    var div = document.getElementById(id);
    div.style.display = 'none';
}

function lockWindow(id) {
    var divLock = document.getElementById(id);
    //    divLock.style.width='1500px';
    //    divLock.style.height='1400px';
    //var width=screen.width+'px';
    //var height=screen.height+400+'px';
    //divLock.style.width=width;
    //divLock.style.height=height;
    divLock.style.display = 'block';
//alert("Ancho: "+divLock.style.width+" Largo: "+divLock.style.height);
}
function unlockWindow(id) {
    var divLock = document.getElementById(id);
    //var width=screen.width+'px';
    //var height=screen.height+'px';
    //divLock.style.width=width;
    //divLock.style.height=height;
    divLock.style.display = 'none';
}

function getElementPosition(elemID) {
    var offsetTrail = document.getElementById(elemID);
    var offsetLeft = 0;
    var offsetTop = 0;
    while (offsetTrail) {
        offsetLeft += offsetTrail.offsetLeft;
        offsetTop += offsetTrail.offsetTop;
        offsetTrail = offsetTrail.offsetParent;
    }
    return {
        left: offsetLeft,
        top: offsetTop
    };
}

function setPositionStatus() {
    getElementPosition('usuarioInput').top;
    getElementPosition('usuarioInput').left;
}

function optenerCampo(id) {
    var field = document.getElementById(id);
}

var campoEspecial = "";
function insertCharacter(value) {
    var field = document.getElementById('tabs:' + campoEspecial);
//    field.value+=value;
    ponSimboloEnPos(field, value);
}

function ponSimboloEnPos(field, value) {
    var cadena = field.value;
    var pos = $("input[name='field.id']").getSelectionStart(field.id);
    var cadenaInicio = cadena.substr(0, pos);
    var cadenaFinal = cadena.substr(pos, cadena.length);
    field.value = cadenaInicio + value + cadenaFinal;
    ponCursorEnPos(pos, field);
    simbolosUnicodeDialog.hide();
}

function ponCursorEnPos(pos, field) {
    laCaja = document.getElementById(field.id);

    if (typeof document.selection != 'undefined' && document.selection) {        //método IE
        var tex = laCaja.value;
        laCaja.value = '';
        laCaja.focus();
        var str = document.selection.createRange();
        laCaja.value = tex;
        str.move("character", pos);
        str.moveEnd("character", 0);
        str.select();
    } else if (typeof laCaja.selectionStart != 'undefined') {                    //método estándar
        laCaja.setSelectionRange(pos + 1, pos + 1);
        laCaja.focus();
    }
}

jQuery.fn.getSelectionStart = function(campo) {
    if (this.lengh == 0)
        return -1;
    input = document.getElementById(campo);

    var pos = input.value.length;
    pos = input.selectionStart;

    return pos;
}


function campoNombreFocus(element) {
    var nombreFocus = $(element).attr("id");
    campoEspecial = nombreFocus;
}

function insertCharacterName(value) {
    var field = document.getElementById(campoEspecial);
    field.value += value;
    simbolosNombreDialog.hide();
}

function disable() {
    button = document.getElementById("vistaPrevia");

}

/*
 function disable(){
 disableButtons();
 disableLinks();
 }
 
 function enable(){
 setTimeout(function(){
 enableButtons();
 },8*1000);
 setTimeout(function(){
 enableLinks();
 },8*1000);
 }
 function disableButtons(){
 buttons=document.getElementsByTagName("input");
 for(i=0;i<buttons.length;i++){
 buttons[i].disabled='disabled';
 }
 }
 function disableLinks(){
 links=document.getElementsByTagName("a");
 for(i=0;i<links.length;i++){
 links[i].onclick = function()
 {
 return false;
 }
 }
 }
 function enableButtons(){
 buttons=document.getElementsByTagName("input");
 for(i=0;i<buttons.length;i++){
 buttons[i].disabled='';
 }
 }
 function enableLinks(){
 alert('listo');
 links=document.getElementsByTagName("a");
 for(i=0;i<links.length;i++){
 links[i].onclick = function()
 {
 return true;
 }
 }
 }
 */

var my_window = null;
function mostrarReporte(url)
{
    closepopup();
    my_window = window.open(url, 'mywindow', 'menubar=1,resizable=1,width=850,height=550');
    my_window.moveTo(0, 0);
}
/*
 function closepopup()
 {
 if(false == my_window.closed)
 {
 my_window.close ();
 }
 else
 {
 alert('Window already closed!');
 }
 }
 */
function closepopup()
{
    if (my_window != null) {
        my_window.close();
        my_window = null;
    }
}

//url dinamica
function urlDinamica(id) {
    var iframe = document.getElementById(id);
    iframe.src = iframe.src + new Date();
    alert(iframe.src);
}

var guiaUsuario = null;

function mostrarGuiaUsuario(url)
{
    closeGuiaUsuario();
    guiaUsuario = window.open(url, 'guiaUsuario', 'menubar=1,resizable=1,width=850,height=550');
    //guiaUsuario.document.write('<embed src="content/imagenes/guiaUsuario.pdf#view=fit" type="application/pdf" height="100%" width="100%"/>');
    guiaUsuario.moveTo(0, 0);
    guiaUsuario.focus();
}

function closeGuiaUsuario()
{
    if (guiaUsuario != null) {
        guiaUsuario.close();
        guiaUsuario = null;
    }
}

function hideDiv(id) {
    var component = document.getElementById(id);
    component.style.display = 'none';
}

function showDiv(id) {
    var component = document.getElementById(id);
    component.style.display = '';
}

function changeRowColor(id) {
    document.getElementById(id).style.color = "black";
    var element = document.getElementById(id).parentNode;
    element = element.parentNode;
    element = element.parentNode;
    for (i = 0; i < element.childNodes.length; i++) {
        var node = element.childNodes[i].childNodes[0];
        node.style.color = "black";
        //element.childNodes[i].style.color="yellow";
    }
}

function onFocusCombo(id) {
    var element = document.getElementById('tabs:' + id);
    //var option=element.options[element.selectedIndex];
    element.focus();
}

function moveFocus(id) {
    var element = document.getElementById(id);
    element.focus();
}

function mascaraExpediente(e, id) {
    tecla = (document.all) ? e.keyCode : e.which;
    //teclas(flechas de navegacion, inicio, fin y shif)
    if (e.which >= 60 && e.which <= 90 || e.which >= 48 && e.which <= 57) {
        var element = document.getElementById(id);
        if (element.value.length == 2) {
            element.value = element.value + "/";
        } else if (element.value.length == 4) {
            element.value = element.value + "/";
        } else if (element.value.length == 9) {
            element.value = element.value + "/";
        }
    }
}

function deseleccionarRadioButton(id) {
    var radioButton1 = document.getElementById(id + ':0');
    var radioButton2 = document.getElementById(id + ':1');
    //radioButton2.outerHTML='<input id="tabs:optionsEstablecimiento:1" name="tabs:optionsEstablecimiento" type="radio" value="0">';
    //radioButton1.outerHTML='<input id="tabs:optionsEstablecimiento:0" name="tabs:optionsEstablecimiento" type="radio" value="1">';
    if (radioButton1 != null && radioButton2 != null) {
        radioButton1.checked = false;
        radioButton1.defaultChecked = false;
        radioButton2.checked = false;
        radioButton2.defaultChecked = false;
        quitarEstiloChecked(id + ':0');
        quitarEstiloChecked(id + ':1');
    }
//alert(radioButton1.outerHTML);
//alert(radioButton2.outerHTML);

//radioButton=document.getElementById(id+':0');
//radioButton.checked='';
}

function quitarEstiloChecked(id) {
    var element = document.getElementById(id);
    element = element.parentNode;
    element = element.parentNode;
    element = element.childNodes[1];
    element.className = 'ui-radiobutton-box ui-widget ui-corner-all ui-radiobutton-relative ui-state-default';
    element = element.childNodes[0];
    element.className = 'ui-radiobutton-icon';
}

function ajustarCombos() {
    alert('combos');
    var combos = document.getElementsByTagName("select");
    for (i = 0; i < combos.length; i++) {
        alert(combos[i].id);
    }
}

var chkAceptarTerminos = false;

function msgAceptarTerminos() {
    var a = confirm('Deberá tildar la Declaración Bajo Protesta y oprimir Aceptar, de lo contrario perderá los anexos cargados. ¿Desea regresar a la ventana anterior?');
    if (a == true)
    {
        singleAnexoDialog.show();
    }
}

function cerrarAnexos() {
    singleAnexoDialog.hide();
    chkAceptarTerminos = true;
}

function rellenarIzquierda(valor, longitud, character) {
    var leftPadding = "";
    for (i = valor.length; i < longitud; i++) {
        leftPadding += character;
    }
    valor = leftPadding + valor;
    return valor;
}

function validarNumPubIntPCT(element) {
    if (element != null && element != undefined) {
        if (element.value.length >= 8) {
            var reOne = new RegExp('WO [0-9]{4}\/[0-9]{1,6}');
            if (element.value.match(reOne) != null && element.value.match(reOne) == element.value) {
                var partOne = element.value.substr(8);
                element.value = element.value.substring(0, 8) + rellenarIzquierda(partOne, 6, "0");
            } else {
                alert('El formato del número de publicación internacional es incorrecto, debe seguir el formato de WO 1999/056366');
            }
        } else {
            alert('El formato del número de publicación internacional es incorrecto, debe seguir el formato de WO 1999/056366');
        }
    }
}

function nPosiciones(element, posiciones) {
    if (element.value != null && element.value != undefined && element.value.length != 0) {
        for (i = element.value.length; i < posiciones; i++) {
            element.value = "0" + element.value;
        }
    }
}

function validarDigitosNumeroSerie(element) {
    if (element.value == '000000') {
        alert('No es un numero valido(' + element.value + ')');
        element.value = '';
    }
}

function validarExpediente(id) {
    var element = document.getElementById(id);
    if (element != null && element != undefined) {
        if (element.value.length >= 10) {
            var codOficina = element.value.substr(0, 2);
            if (!(codOficina == 'GT' || codOficina == 'JL' || codOficina == 'NL'
                    || codOficina == 'PA' || codOficina == 'YU' || codOficina == 'MX' && element.value.substring(2, 3) == '\/')) {
                alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456 (código de la oficina)');
            } else {
                var tipoExp = element.value.substring(3, 4);
                if (!(tipoExp == 'a' || tipoExp == 'f' || tipoExp == 't' || tipoExp == 'u' && element.value.substring(4, 5) == '\/')) {
                    alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456 (tipo de expediente)');
                } else {
                    if (!isNaN(element.value.substring(5, 9)) && element.value.substring(9, 10) == '\/') {
                        var anioExp = parseInt(element.value.substring(5, 9));
                        var fechaActual = parseInt(new Date().getFullYear());
                        if (codOficina != 'MX' && anioExp > 2006) {
                            alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456. El año no corresponde con la oficina');
                        } else if (codOficina == 'MX' && anioExp <= 2006) {
                            alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456. El año no corresponde con la oficina');
                        } else if (anioExp > fechaActual) {
                            alert('El año de presentación de la solicitud divisional no puede ser mayor al año actual.');
                        } else {
                            if (!isNaN(element.value.substring(10))) {
                                var folioExp = element.value.substring(10);
                                if (folioExp.length > 0) {
                                    var leftPadding = "";
                                    for (var i = folioExp.length; i < 6; i++) {
                                        leftPadding += "0";
                                    }
                                    element.value = element.value.substring(0, 10) + leftPadding + folioExp;
                                } else {
                                    alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456. (folio del expediente vacio)');
                                }
                            } else {
                                alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456. (folio del expediente)');
                            }
                        }
                    } else {
                        alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456 (año del expediente)');
                    }
                }

            }
        } else {
            alert('El formato Divisional de la Solicitud es incorrecto, debe seguir el formato de MX/a/2011/123456');
        }
    }
}

function isSelected() {

    var items = document.getElementById("formaFirma:multiCars_selection");
    if (items.value != "")
    {
        dlgFirm.show();
    } else {
        alert("No ha seleccionado ningun registro a firmar");

    }

}

function getInnerTextSelect(id) {
    var pos = document.getElementById(id).selectedIndex;
    var valor = document.getElementById(id).options[pos].innerHTML;
    return valor;
}

function getValueSelect(id) {
    var pos = document.getElementById(id).selectedIndex;
    var valor = document.getElementById(id).options[pos].value;
    return valor;
}

function mostrarRuta(ruta) {
//    document.getElementById('tabs:txtRutaImagen').value=ruta;
}

/*Funcion que agrega 1 Reinvicacion cuando existe el titulo de la invencion y se seleeciona la pestana Reinvicaciones*/
function tabChanged(index) {
    //El num. 6 corresponde a la pestana de Memoria Tecnica: 
    //NOTA: eliminar las otras opcion (7, 8) despues de que hayan aceptado la unificacion de pestana (descripcion, reivindicaciones)
//   if(index==7)
//       agregarReivindicaciones();
//   else 
//       if(index==8)
//       agregarReivindicacionMemTec();
//   if(index==8){
//      agregaAnexos();
//   }


}

function hideOrShow(accion) {
    var divLock = document.getElementById("divStatusCopy");
    var divLockWindow = document.getElementById("divLockWindow");

    if (accion == 0) {
        divLock.style.display = 'block';
        divLockWindow.style.display = 'block';
    } else {
        divLock.style.display = 'none';
        divLockWindow.style.display = 'none';
    }
}
function justNumbers(e)
{
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
        return true;

    return /\d/.test(String.fromCharCode(keynum));
}

function MaxMinFechaDiv() {
    MaxMinFechaDiv();

}

function seleccionarArchivoDescripcion() {
    $("input[id='fileDescripcion:dialogFileDesc']").click();
}


function seleccionarArchivoPersonalidad() {
    $("input[id='fileDocPersonalidad:dialogFilePersonalidad']").click();
}
function seleccionarArchivoPrioridad() {
    $("input[id='filePrioridad:dialogFilePrioridad']").click();
}

function seleccionarArchivoResumen() {
    $("input[id='fileResumen:dialogFileResumen']").click();
}

function seleccionarArchivoImagenes() {
    $("input[id='fileFiguras:archivosImagenes']").click();
}
function seleccionarArchivoImagenesOK() {
    $("input[id='fileImagenes:dialogImagenes']").click();
}
function seleccionarArchivoDivulgacion() {
    $("input[id='fileDivulgacion:archivoDivPre']").click();
}

function seleccionarArchivoTraduccion() {
    $("input[id='fileTraduccionSimple:dialogArchTraduccion']").click();
}

function seleccionarArchivo(id) {
//    $("input[id='tabs:cmbSeleccionArchivo_input']").click();
    cadena = "input[id='tabs:" + id + "']";
    $(cadena).click();
}

function toUpperCaseFirst(e, field, pestana) {
    var tb = field;
    var cadena = field.value;
    var returnString = "";
    var len;
    var tipoPersona = 0;

    if (pestana == 1)
        tipoPersona = $("select[name='tabs:tipoPersonaSolicitante'] option:selected").val();

    if (tipoPersona == 2 && pestana == 1)
        toUpperCase(e, field);
    else if (tipoPersona == 0 || pestana == 1) {

        var palabras = cadena.split(" ");

        len = palabras.length;
        for (i = 0; i < len; i++) {
            if (i != (len - 1)) {
                returnString = returnString + ucFirst(palabras[i]) + " ";
            }
            else {
                returnString = returnString + ucFirst(palabras[i]);
            }
        }

        tb.value = returnString;

    }

}

function ucFirst(string) {
    return string.substr(0, 1).toUpperCase() + string.substr(1, string.length).toLowerCase();
}