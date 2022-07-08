var app = new Vue({
    el: '#sportFacilityInfo',
    data:{
        sportFacility: null,
		treninzi: {},
		komentari: {}
    },
	mounted() {
		axios.get('rest/sportFacilities/reviewed')
		.then(response => {
			this.sportFacility = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.sportFacility.name)
			.then(response => {this.treninzi = response.data})
			axios.get('rest/comments/approvedCommentsForFacility/' + this.sportFacility.name)
			.then(response => {this.komentari = response.data})
			
			var iconFeature = new ol.Feature({
		  	geometry: new ol.geom.Point(ol.proj.fromLonLat([this.sportFacility.location.longitude, this.sportFacility.location.latitude])),
		  	name: this.sportFacility.name,
			});
	 		var map = new ol.Map({
		         target: 'map',
		         layers: [
		           new ol.layer.Tile({
		             source: new ol.source.OSM()
		           }),
		           new ol.layer.Vector({
		        	      source: new ol.source.Vector({
		        	        features: [iconFeature]
		        	      }),
		        	      style: new ol.style.Style({
		        	        image: new ol.style.Icon({
		        	          anchor: [0.5, 46],
		        	          anchorXUnits: 'fraction',
		        	          anchorYUnits: 'pixels',
		        	          src: 'img/marker.png' 
		        	        })
	        	      })
        	    })
        	  
         ],
         
         view: new ol.View({
           center: ol.proj.fromLonLat([this.sportFacility.location.longitude, this.sportFacility.location.latitude]),
           zoom: 4 
         }),

       });
			
	})
	
	}
})