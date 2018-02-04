
 
 var map = tomtom.map('map', {
     key: 'afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS',
     source: 'vector',
     basePath: '/assets/sdk'
 }).setView([37.7876172, -122.3988267], 13)

 tomtom.L.marker([37.7876172, -122.3988267], {
    draggable: true
}).addTo(map);

tomtom.routingKey('afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS')

function addMarkers(feature) {
    var startPoint, endPoint;
    if (feature.geometry.type === 'MultiLineString') {
        startPoint = feature.geometry.coordinates[0][0].reverse(); //get first point from first line
        endPoint = feature.geometry.coordinates.slice(-1)[0].slice(-1)[0].reverse(); //get last point from last line
    } else {
        startPoint = feature.geometry.coordinates[0].reverse();
        endPoint = feature.geometry.coordinates.slice(-1)[0].reverse();
    }
    tomtom.L.marker(startPoint).addTo(map);
    tomtom.L.marker(endPoint).addTo(map);
}

// tomtom.routing()
//     .locations('37.7876172,-122.3988267:37.7868796,-122.3989018')
//     .go().then(function(routeJson) {
//         var route = tomtom.L.geoJson(routeJson, {
//             onEachFeature: addMarkers,
//             style: {color: '#00d7ff', opacity: 0.8}
//         }).addTo(map);
//         map.fitBounds(route.getBounds(), {padding: [5, 5]});
//     });

var activeRoute

let locationsList = [
    {lat: 37.7899221, lon: -122.4093303},
    {lat: 37.7902049, lon: -122.4095533},
    {lat: 37.7906034, lon: -122.4055514},
    {lat: 37.7922906, lon: -122.4055836},
    {lat: 37.7899221, lon: -122.4093303}
]


/*
37.7899221,-122.4093303,18.68z
37.7902049,-122.4095533,17z
37.7906034,-122.4055514,17z
37.7922906,-122.4055836,17z
37.7899221,-122.4093303,18.68z
*/

const getSupportingPoints = _ => locationsList

var routeOnMapView = tomtom.routeOnMap().addTo(map)

routeOnMapView.draw(locationsList);

tomtom.routing()
.locations(locationsList)
// .supportingPoints(getSupportingPoints())
.go()
.then(function(routeJson) {
    routeOnMapView.clearRoute();
    if (activeRoute) {
        map.removeLayer(activeRoute);
    }
    try {
        activeRoute = tomtom.L.geoJson(routeJson, {
            style: {
                color: '#00d7ff'
            }
        }).addTo(map);

        map.fitBounds(activeRoute.getBounds());
        tryEnablingSubmit();
        removeSubmitLoader();
    } catch (err) {
        handleError(err);
    }
}, null);