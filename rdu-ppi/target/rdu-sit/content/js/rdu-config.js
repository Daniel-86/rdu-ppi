

$(function()
{
    $(document).ajaxComplete(function(){
        $(".ui-selectonemenu").removeAttr("style");    
//        $("table tbody tr").removeClass("ui-state-highlight");
    
    });
    $(".ui-selectonemenu").removeAttr("style");           
//    $("table tbody tr").removeClass("ui-state-highlight");
    
});

function blurAddClass(){
    var message = $("#encuestaForm\\:variacion-precio-message").val();
    alert("MENSAJE DE PRUEBAS   "+message);    
}

/* Funciones para el efecto del borde verde de los input */
function focusAddClass(element)
{    
    var paisSeleccionado = $(element).attr("value");
    if(paisSeleccionado!==918)
        crearNuevaEncuesta();
}

function changePais(element) {    
    if($(element).attr("name").indexOf("Solicitante") > 1)
        solicitantesDomi(element);
    else ($(element).attr("name").indexOf("Inventor") >1)
        iventoresDomi(element);
}

var savedPaisInventor =0;
var savedPaisSolicitante =0;
var pais_mexico = 918;

function iventoresDomi(element){    
    var paisSeleccionado = $(element).attr("value");
    
    if(paisSeleccionado==pais_mexico){
        savedPaisInventor =1;
        cambioDomicilioInventor();
    }else if(savedPaisInventor > 0){
        savedPaisInventor = 0;
        cambioDomicilioInventor();
    }
}

function oculataDocumento(){    
    
var fechaCapturada = document.getElementById("tabs:fechaDivPrevia_input").value;

        oculDom();

}


function solicitantesDomi(element){    
    var paisSeleccionado = $(element).attr("value");
    
    if(paisSeleccionado==pais_mexico){
        savedPaisSolicitante =1;
        cambioDomicilio();
    }else if(savedPaisSolicitante > 0){
        savedPaisSolicitante = 0;
        cambioDomicilio();
    }
}
var capturaSolicitudPatentes = 0;