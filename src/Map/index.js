import React from 'react';
import ReactDOM from 'react-dom';
import { getPoint } from '../services/pointsService.js';

const tomtom = window.tomtom;
class Map extends React.Component {
  constructor() {
      super();
      this.state = {};
  }
  componentDidMount() {
      var map = this.map = tomtom.map(ReactDOM.findDOMNode(this), {
          key: 'afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS',
          source: 'vector',
          basePath: 'http://api.tomtom.com/maps-sdk-js/4.15.0/examples/sdk'
      }).setView([37.7876172, -122.3988267], 13);

     tomtom.routingKey('afvzENxqGEdRkwVeNBMF3tbMdVcG0AlS')
     //  CREATING ROUTE; with getPoints services
      getPoint().then((points) => {

        this.setState({points: points.data})
        var activeRoute
        let locationsList = [];
        for (var i = 0; i < points.data.match; i++) {
          locationsList.push(points.data.payload[i].location);
        }

        var routeOnMapView = tomtom.routeOnMap().addTo(map)

        routeOnMapView.draw(locationsList);

        tomtom.routing()
        .locations(locationsList)
        .go()
        .then(function(routeJson) {
            routeOnMapView.clearRoute();
            if (activeRoute) {
                map.removeLayer(activeRoute);
            }
            try {
                activeRoute = tomtom.L.geoJson(routeJson, {
                    style: {
                        color: '#ff2244'
                    }
                }).addTo(map);

                map.fitBounds(activeRoute.getBounds());
                this.map.tryEnablingSubmit();
                this.map.removeSubmitLoader();
            } catch (err) {

            }
          }, null);
      });

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
        console.warn('render',this.state.points);
        return (
            <div id='map' style={divStyle}></div>
        );
    }

}
export default Map;
