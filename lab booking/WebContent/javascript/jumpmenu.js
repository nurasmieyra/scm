function toggleMe(index){
	var i;
	var a;
    indexArray = new Array("DALY", "WKLY", "EWDY", "MNLY");
    
	switch (index) {
		case 0: 
			break;
		case 1:
			a = "DALY"; 
			break;
		case 2: 
			a = "WKLY"; 
			break;
		case 3: 
			a = "EWDY"; 
			break;
		case 4: 
			a = "MNLY"; 
			break;
		default: 
			break;
	}

	for(i=0;i<indexArray.length;i++) {
		document.getElementById(indexArray[i]).style.display="none";
	}
	
 	var e=document.getElementById(a);
 	if(!e)return true;
 	
 	if(e.style.display=="none"){
		e.style.display="block"
   } else {
        e.style.display="none"
   }
   return true;
}

function getDisplayState(divID) {
	return document.getElementById(divID).style.display;
}

function getSelected(elementID) {
	return document.getElementById(elementID).selected;
}