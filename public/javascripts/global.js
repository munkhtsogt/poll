jQuery(document).ready(function(){

	$('.grid').mouseenter(function(){
		$(this).addClass('grid-hover');
	}).mouseleave(function(){
		$(this).removeClass('grid-hover');
	});

	$('.grid-oc').click(function(){
		$('.transperncy-overlay').fadeIn(function() {
			window.setTimeout(function(){
				$('.popup-window').addClass('popup-window-visible');
			}, 50);
		});
		return false;
	});

	$('.popup-window-close').click(function(){
		$('.transperncy-overlay').fadeOut().end().find('.popup-window').removeClass('popup-window-visible');
	});
	
	$('.controller li').click(function(){
        
        $('.controller li').removeClass('active');
        $(this).addClass('active');
        
        $('.tabContent').hide();
        
        $($(this).attr('tab')).show();
        
        return false;
    });
});