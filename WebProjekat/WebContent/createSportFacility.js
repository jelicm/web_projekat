var app = new Vue({
    el: '#createSportFacility',
    data:{
        naziv: '',
		tip: '',
		geoDuzina: '',
		geoSirina: '',
		ulica: '',
		broj: '',
		grad: '',
		kod: '',
		slika: '',
		izabraniMenadzer: '',
		menadzeri: {},
		korisnickoIme: '',
		lozinka: '',
		ime: '',
		prezime: '',
		pol: '',
		datumRodjenja: ''
    },
    mounted(){
		v = this;
		axios.get('rest/users/availableManagers')
		.then(response => {this.menadzeri = response.data})
		
		function transliterate(word){
	    var answer = "", 
	      a = {};
	
	   a["А"]="A";a["Б"]="B";a["В"]="V";a["Г"]="G";a["Д"]="D";a["Ђ"]="DJ";a["Е"]="E";a["Ж"]="Z";a["З"]="Z";a["И"]="I";
		a["Ј"]="J";a["К"]="K";a["Л"]="L"; a["Љ"]="LJ";a["М"]="M";a["Н"]="N";
	  a["Њ"]="NJ";a["О"]="O";a["П"]="P";a["Р"]="R";a["С"]="S";a["Т"]="T";a["Ћ"]="C";a["У"]="U";a["Ф"]="F";a["Х"]="H";
	   a["Ц"]="C";a["Ч"]="C";a["Џ"]="DZ";a["Ш"]="S";a["а"]="a";a["б"]="b";a["в"]="v";a["г"]="g";a["д"]="d";a["ђ"]="dj";a["е"]="e";
	   a["ж"]="z";a["з"]="z";a["и"]="i";a["ј"]="j";a["к"]="k";a["л"]="l";a["љ"]="lj";a["м"]="m";a["н"]="n";a["њ"]="nj";a["о"]="o";
	   a["п"]="p";a["р"]="r";a["с"]="s";a["т"]="t";a["ћ"]="c";a["у"]="u";a["ф"]="f";a["х"]="h";a["ц"]="c";
	   a["ч"]="c";a["џ"]="dz";a["ш"]="s";
	
	   for (i in word){
	     if (word.hasOwnProperty(i)) {
	       if (a[word[i]] === undefined){
	         answer += word[i];
	       } else {
	         answer += a[word[i]];
	       }
	     }
	   }
	   return answer;
	}
		
		function simpleReverseGeocoding(lon, lat) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + lon + '&lat=' + lat).then(function(response) {
          return response.json();
        }).then(function(json) {
          v.grad = transliterate(json.address.city);
		  v.kod = json.address.postcode;
		  v.ulica = transliterate(json.address.road);
		  v.broj = 5;
        })
      }
		
		let vectorLayer = new ol.layer.Vector({
    		source: new ol.source.Vector(),
		});
		var map = new ol.Map({
		         target: 'map',
		         layers: [
		           new ol.layer.Tile({
		             source: new ol.source.OSM()
		           }),
					vectorLayer

         	],
         
         view: new ol.View({
           center: ol.proj.fromLonLat(["19.83", "45.26"]),
           zoom: 4 
         }),

       });
	
		
		map.on('click', function (evt) {
		  coordinate = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
			v.geoDuzina = coordinate[0]
			v.geoSirina = coordinate[1]
			simpleReverseGeocoding(coordinate[0], coordinate[1]);
           let marker = new ol.Feature(new ol.geom.Point(evt.coordinate));
   		   marker.setStyle(
           new ol.style.Style({
            image: new ol.style.Circle({
                radius: 5,
                fill: new ol.style.Fill({color: 'red'})
            })
          })
       );
		vectorLayer.getSource().clear()
        vectorLayer.getSource().addFeature(marker);
        });
    },
    methods: {
	
		
        create: function(event){
	
            event.preventDefault()
            let array = this.slika.split("\\")
            let slikaPutanja = array[array.length - 1]
            if(!this.naziv || !this.tip || !this.geoDuzina || !this.geoSirina || !this.ulica || !this.broj || !this.grad
            	|| !this.kod || !slikaPutanja || !this.izabraniMenadzer){
				alert("Postoji nepopunjeno polje forme za sportski objekat!")
				return
			}
			axios.post('rest/sportFacilities/createSportFacility/' + this.izabraniMenadzer, 
	            {"name": this.naziv, "type": this.tip, "trainings": null, "status": "NE_RADI",
	            "location": {"longitude": this.geoDuzina, "latitude": this.geoSirina, "address": {"number" : this.broj,
	            "street" : this.ulica, "city" : this.grad, "zipCode" : this.kod} }, "image" : slikaPutanja, "averageRating" : 0.0,
	            "workTime" : "08:00-21:00", "deleted": false})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime sportskog objekta nije jedinstveno!")
	            })	
        },
	    registerManager: function(){
			if(!this.korisnickoIme || !this.lozinka || !this.ime || !this.prezime || !this.pol || !this.datumRodjenja){
				alert("Postoji nepopunjeno polje forme za registrovanje menadzera!")
				return
			}
			axios.post('rest/users/registerManager', 
	            {"name": this.ime, "surname": this.prezime, "gender": this.pol,
	            "dateOfBirth": this.datumRodjenja, "role": "MENADZER", "username": this.korisnickoIme,
	            "password": this.lozinka, "sportFacility": null, "deleted": false})
	            .then(this.izabraniMenadzer = this.ime + " " + this.prezime)
	            .catch(function(){
	                alert("Korisnicko ime menadzera nije jedinstveno!")
            	})
		}
    }
})