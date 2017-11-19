/*
	Popup div plugin
	author@ Munkhtsogt Tsogbadrak - munkhuu48@gmail.com
	
	usage:
		<html>
			<head>
				<script type="text/javascript"  src="popup.js"></script>
			</head>
			<div id="popUp" style="display:none;">
				DO WHAT EVER YOU WANT
			</div>
			<input type="text" id="myInput" />
		</html>
		<script type="text/javascript">
			$(function(){
				$('#myInput').popupDiv('#popUp');
			});
		</script>
*/

jQuery.fn.popupDiv = function (divToPop)
{
	var input = $(this);
	input.focus(function (e)
	{
			 	var pos=input.position();
			 	var fp = input.parent().parent().parent().parent().parent().position();
				var h=input.height();
				var w=input.width();
				$(divToPop).css(
				{ 
						left: pos.left, 
						top: (fp.top < 0) ? pos.top - $(divToPop).height(): pos.top + h + 10, 
					 	position: "absolute",
					 	display: "none",
					 	width: '300px',
					 	height: $(divToPop).height(),
					 	'max-height': '980px',
					 	overflow: 'auto',
					 	'-moz-border-radius': '8px', 
					 	'-khtml-border-radius': '8px',
					 	'-webkit-border-radius': '8px', 
					 	'border-radius': '8px',
					 	'background-color': "#FFF",
					 	border:'1px solid #999',
					 	'z-index':500,
				});
			 $(divToPop).show();
			 $(divToPop).attr("input-id", input.attr("id"))
	});
};
