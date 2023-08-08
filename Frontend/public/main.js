//var map = Karteanlegen(Welcher div Container).woIstDasZentrumDerKarte([Breitengrad, Längengrad], Zoomstufe (0 = ganze Welt))
var map = L.map('map').setView([48.745837, 9.105398], 15);
//Wie soll Karte aussehen = Mosaikbauteile
//TilesHerunterLaden mit z= Zoomlevel, x,y= BreitenundLängengrad
L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    maxZoom: 19, 
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMep</a> contributers'
}).addTo(map);

//Add layer to map
var myFeatureLayer = L.layerGroup().addTo(map);



//Add a marker at university of stuttgart onto the layer
//var marker = L.marker([48.745837, 9.105398]).addTo(myFeatureLayer);


//Initaliziere verschiedene Dinge I guess
var startNode = document.getElementById("startNode");
var endNode = document.getElementById("endNode");
var latlngs = Array();


//Sets markers, popups, start and end Node when user clicks on the map.
map.on('click', onMapClick);     
function onMapClick(e) { // was ist eigentlich e ??
    var markerMouseclick = L.marker();
    var popup = L.popup();
    
    
    //Set start and end node when clicking on the map - if they are not set yet
    if (startNode.innerHTML == "Click on map to set the start node."){
        //Set marker
        markerMouseclick
        .setLatLng(e.latlng)
        .addTo(myFeatureLayer);
        //Create popup
        popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString() + "this is your start node.")
        .openOn(myFeatureLayer);
        
        //write cords onto website
        startNode.innerHTML = "Start Node is at: " + e.latlng.toString();
        //ForPolyline later
        latlngs.push(e.latlng);

    } else if (endNode.innerHTML == "After you set the start node, click again on map to set the end node."){
        //Set marker
        markerMouseclick
        .setLatLng(e.latlng)
        .addTo(myFeatureLayer);
        //Create popup
        popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString() + "this is your end node.")
        .openOn(myFeatureLayer);

        //write cords onto website
        endNode.innerHTML = "End Node is at: " + e.latlng.toString();
        //For Polylinelater
        latlngs.push(e.latlng);

        //Create polyline between start and end node
        setPolyline();
    } else {
        //If user tries to click more than twice on the map
        window.alert("Click the reset button to set new nodes.")
    }
}

//Draws line between markers
function setPolyline(){
    //Create a red polyline from an array of LatLng points
    var polyline = L.polyline(latlngs, {color: 'red'}).addTo(myFeatureLayer);
    //Zoom the map to the polyline
    map.fitBounds(polyline.getBounds());

}


//TODO mithilfe von Backend stuff
function SendInfo(){
    const params = new URLSearchParams({startNode,endNode}); // maybe bekomme ich damit nacher ein mismatch
    var url ="Pfad?val="+params; // welcher Pfad bzw Datei muss hier rein, wenn ich auf Graph klasse zugreifen will 

    if(window.XMLHttpRequest){
        var request= new XMLHttpRequest();
        request.onreadystatechange=getInfo();
        request.open("GET",url,true);
        request.send();


    }

    function getInfo(){
        if(request.readyState ==4){
            var val = request.responseText();
            // ich weiß nicht was hier passiert außerdem weiß ich nicht wo ich ausgabe hintun soll 
            // was macht <span>'amit</span> weil das muss in html file noch rein   
            document.getElementById('amit').innerHTML=val;
          }
    }

       
        
    
}

//Reset everything - nodes, polyline, zoom, array, ... 
function clickedReset(){
    startNode.innerHTML = "Click on map to set the start node.";
    endNode.innerHTML = "After you set the start node, click again on map to set the end node.";
    
    latlngs = [];
    myFeatureLayer.clearLayers();
    map.setView([48.745837, 9.105398], 15);

    window.alert("You reset the nodes. Please set new nodes");
}


