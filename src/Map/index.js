import React from 'react';
import ReactDOM from 'react-dom';
const tomtom = window.tomtom;
class Map extends React.Component {
  componentDidMount() {

      var map = this.map = tomtom.map(ReactDOM.findDOMNode(this), {
          key: 'afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS',
          source: 'vector',
          basePath: 'http://api.tomtom.com/maps-sdk-js/4.15.0/examples/sdk'
      }).setView([37.7876172, -122.3988267], 13);

      tomtom.L.marker([37.7876172, -122.3988267], {
         draggable: true
     }).addTo(map);

     tomtom.routingKey('afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS')

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
             this.map.tryEnablingSubmit();
             this.map.removeSubmitLoader();
         } catch (err) {
             this.map.handleError(err);
         }
       }, null);
      // Re-Render MAp
      this.map.invalidateSize()

    }

    componentWillUnmount() {
        this.map.off('click', this.onMapClick);
        this.map = null;
    }

    onMapClick = () => {
        // Do some wonderful map things...
        console.warn('CLICK ON MAP');
    }

    render() {
      const divStyle = {
        width:  '800px',
        height: '500px',
        border: '2px solid red'
        };
        return (
            <div id='map' style={divStyle}></div>
        );
    }

}
export default Map;
