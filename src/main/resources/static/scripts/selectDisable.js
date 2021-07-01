var sl1 = document.getElementById('langFrom');
var sl2 = document.getElementById('langTo');
var prev;

sl1.onfocus = function() {
	prev = sl1.value;
};

sl2.onfocus = function() {
	prev = sl2.value;
};

sl1.addEventListener('change', function() {
    var opts = sl2.childNodes;
    
    if(sl1.value == sl2.value) {
    	for(var i = 0; i < opts.length; i++) {
    		if(opts[i].value == prev) 
    			opts[i].selected = true;
    	}
    } 
    prev = sl1.value;
});


sl2.addEventListener('change', function() {
    var opts = sl1.childNodes;
    
    if(sl2.value == sl1.value) {
    	for(var i = 0; i < opts.length; i++) {
    		if(opts[i].value == prev)
    			opts[i].selected = true;
    	}
    }
   	prev = sl2.value;
});