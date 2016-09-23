$(document).ready(function(){

$('.has-sub').click( function() {
    if($( ".has-sub" ).children('ul').is( ":visible" ) && !($( this ).children('ul').is( ":visible" )))
    {
        $( ".has-sub" ).children('ul').slideToggle();
    }
    else
    jQuery(this).children('ul').slideToggle();
});

$('.menu-trigger').click(function(){

$('nav').slideToggle();

});

});


