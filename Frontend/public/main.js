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
var startNodeLat;
var startNodeLon;
var endNodeLat;
var endNodeLon;


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
        startNode.innerHTML = e.latlng.toString();
        //ForPolyline later
        latlngs.push(e.latlng);


        //Save Latitude and Longitude in global variables to calculate distance later
        startNodeLat = e.latlng.toString().substring(7, e.latlng.toString().indexOf(","));
        startNodeLon = e.latlng.toString().substring(e.latlng.toString().indexOf(",")+2, e.latlng.toString().indexOf(")"));

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
        endNode.innerHTML = e.latlng.toString();
        //For Polylinelater
        latlngs.push(e.latlng);

        //Create polyline between start and end node
        setPolyline();


        //Save Latitude and Longitude in global variables to calculate distance later
        endNodeLat = e.latlng.toString().substring(7, e.latlng.toString().indexOf(","));
        endNodeLon = e.latlng.toString().substring(e.latlng.toString().indexOf(",")+2, e.latlng.toString().indexOf(")"));

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

function sendInfo(){
    if ((startNode.innerHTML != "Click on map to set the start node.") && (endNode.innerHTML != "After you set the start node, click again on map to set the end node.")) {


        var url ="http://localhost:8000/dijkstra?start_long="+startNodeLon+"&start_lat="+startNodeLat+"&end_long="+endNodeLon+"&end_lat="+endNodeLat; // welcher Pfad bzw Datei muss hier rein

        if(window.XMLHttpRequest){
            var request= new XMLHttpRequest();
            request.onreadystatechange=getInfo;
            request.open("GET",url,true);
            request.send();
        }

        function getInfo(){
            if(request.readyState ==4){
                var val = request.responseText;
                // ich weiß nicht was hier passiert außerdem weiß ich nicht wo ich ausgabe hintun soll 
                // was macht <span>'amit</span> weil das muss in html file noch rein   
                document.getElementById('distance').innerHTML=val;
            }
        }
    } else {
        window.alert("You have to set the startnode and endnode first. (You set the startnode and endnode by clicking on the map.")
    }
}

//Reset everything - nodes, polyline, zoom, array, ... 
function clickedReset(){
    startNode.innerHTML = "Click on map to set the start node.";
    endNode.innerHTML = "After you set the start node, click again on map to set the end node.";
    
    latlngs = [];
    myFeatureLayer.clearLayers();
    map.setView([48.745837, 9.105398], 15);

   
}