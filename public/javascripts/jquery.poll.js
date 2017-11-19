/*
 * Poll - jQuery plugin 
 * @author Munkhtsogt Tsogbadrakh <munkhuu48@gmail.com>
 */

(function($){

  $.fn.poll = function( options )
  {
    var settings = $.extend(
    {
      width: '',
      height: '',
      parameters: [],
      values: []
    }, options);
  
    return this.each(function()
    {
      var $this = $(this);
      
      var canvas = document.createElement('canvas');
      
      canvas.setAttribute('width', settings.width);
      canvas.setAttribute('height', settings.height);
      //canvas.setAttribute('style', "border:solid 1px #798087;");
      
      var context = canvas.getContext('2d');
      
      canvas.width=canvas.width;
      
      var parameters = settings.parameters;
      var values = settings.values;
      var offset = canvas.width/(parameters.length + 1);
      var maxchar = 0;
      var total = 0;
      for(var i=0; i<values.length; i++) {
        total += parseInt(values[i], 10);
        var parameter = parameters[i];
        maxchar = (maxchar <= parameter.length) ? parameter.length : maxchar;
      }
      
      var offsets = [];
      
      context.fillStyle = "black";
      context.font = "12px sans-serif";
      context.fillText("Total voters: " + String(total), canvas.width - 100, canvas.height - 50);
      
      total = (total == 0) ? 1 : total;
      
      for(var i=0; i<parameters.length; i++){
        var parameter = parameters[i];
        context.fillStyle = "black";
        context.font = "12px sans-serif";
        context.fillText(parameter, 20, (i + 1)* offset);
      }
      
      for(var i=0; i<parameters.length; i++)
      {
        context.fillStyle = "black";
        context.font = "19px sans-serif";
        context.fillText("+", 234 + maxchar * 10, (i + 1)* offset + 17);
        var oset = new Object();
        oset['left'] = 230 + maxchar * 10;
        oset['right'] = 250 + maxchar * 10;
        oset['top'] = (i + 1) * offset;
        oset['bottom'] = (i + 1)* offset + 20;
        oset['parameter'] = parameters[i];
        offsets.push(oset);
      }
      
      for(var i=0; i<parameters.length; i++){
        var parameter = parameters[i];
     
        var percent = parseFloat((parseInt(values[i], 10) * 100)/total).toFixed(2);
        var rwidth = parseInt(percent * 2, 10);  
        drawRect(context, 20 + maxchar * 10, (i + 1)* offset - 20, 200, 40, '', 0.5);
        // small rectangular
        drawRect(context, 230 + maxchar * 10, (i + 1)* offset, 20, 20, '', 0.5);
        
        var color = "#" + Math.floor(Math.random()*16777216).toString(16);
        drawRect(context, 20 + maxchar * 10, (i + 1)* offset - 20, rwidth, 40, color, 0.2);
      }
      
      for(var i=0; i<parameters.length; i++){
    	  context.fillStyle = "black";
          context.font = "15px sans-serif";
          var percent = String(parseFloat((parseInt(values[i], 10) * 100)/total).toFixed(2)) + " %";
          context.fillText(percent, 120 + maxchar * 10, (i + 1)* offset + 5);
      }
      
      $(canvas).mousemove(function myDown(e) 
      {
    	  
    	  var position = $(canvas).position();
          var x = e.offsetX; // - position.left;
          var y = e.offsetY; // - position.top;
          
          for(var i=0; i<offsets.length; i++)
          {  
              if (x>offsets[i].left && x<offsets[i].right && 
                  y>offsets[i].top && y<offsets[i].bottom) 
              {
                var title = "Vote for " + offsets[i].parameter; 
                $(canvas).attr("title", title);     
                break;
              }
              else
        	  {
        		  $(canvas).removeAttr('title')
        	  }  
          }
      });
      $(canvas).mousedown(function myDown(e) 
      {	
    	  var position = $(canvas).position();
          var x = e.offsetX;//-position.left;
          var y = e.offsetY;//-position.top;

          for(var i=0; i<offsets.length; i++)
          {  
              if (x>offsets[i].left && x<offsets[i].right && 
                  y>offsets[i].top && y<offsets[i].bottom) 
              {
                settings.response.call($this, offsets[i].parameter);   
                break;
              } 
          }
      });      
      $this.empty();
      $this.append(canvas);
    });  
  
  };
 
  function drawRect(ctx, x, y, width, height, color, lwidth)
  {
      ctx.beginPath();
      ctx.rect(x, y, width, height);
      ctx.fillStyle = (color) ? color : "rgba(0, 0, 200, 0.08)";
      ctx.fill();
      ctx.lineWidth = (lwidth) ? lwidth : 0.5;
      ctx.strokeStyle = 'black';
      ctx.stroke();
      ctx.closePath();
  }  
  function drawLine(ctx, sx, sy, ex, ey)
  {
    ctx.beginPath();
    
    ctx.moveTo(sx, sy);
    ctx.lineTo(ex, ey);
    
    ctx.closePath();
    ctx.stroke();
  }  
}) (jQuery); 