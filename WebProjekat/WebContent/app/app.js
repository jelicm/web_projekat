var app = new Vue({
    el: '#loginForm',
    data:{
        korisnickoIme: '',
		lozinka: '',
		objekti: {},
		pretrazeniObjekti: {},
		naziv: '',
		tip: '',
		lokacija: '',
		prosecnaOcena: '',
		sortiranoNaziv: 0,
		sortiranoLok: 0,
		sortiranoOcena: 0,
		status: {},
		tipovi: {},
		st: '',
		tip1: '',
		grad: ''
    },

	mounted() {
		v = this;
		function transliterate(word){
	    var answer = "" , 
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
		  	v.pretrazeniObjekti = v.objekti.filter(o => v.grad.toLowerCase().includes(o.location.address.city.toLowerCase()));
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

		axios
			.get("rest/sportFacilities")
			.then((response) => {
				this.objekti = response.data;
				this.pretrazeniObjekti = response.data;	
			});
		axios.get("rest/enums/status")
			 .then((response) => {this.status = response.data});
		
		axios.get("rest/enums/facilityType")
			 .then((response) => {this.tipovi = response.data});
		
	},
	
    methods: {
        login: function(event){
            event.preventDefault()
            if(!this.korisnickoIme || !this.lozinka){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
            axios.get('rest/users/login/' + this.korisnickoIme + '/' + this.lozinka)
            .then(response => {
                location.href=response.data 
            })
            .catch(function(){
                alert("Neispravni kredencijali!")
            })
        },
		pretragaNaziv: function(naziv){
			this.pretrazeniObjekti = this.objekti.filter(o => o.name.toLowerCase().includes(naziv.toLowerCase()));
			
			this.prosecnaOcena = '';
			this.tip = '';
			this.lokacija = '';
		
		},
		pretragaLokacija: function(lokacija){
			this.pretrazeniObjekti = this.objekti.filter(o => (o.location.address.street.toLowerCase().includes(lokacija.toLowerCase()) || o.location.address.city.toLowerCase().includes(lokacija.toLowerCase())));
			this.naziv = '';
			this.prosecnaOcena = '';
			this.tip = '';
			
		},
		pretragaOcena: function(prosecnaOcena){
			this.pretrazeniObjekti = this.objekti.filter(o => o.averageRating == prosecnaOcena);
			this.naziv = '';
			this.tip = '';
			this.lokacija = '';
		},
		pretragaTip: function(tip){
			this.pretrazeniObjekti = this.objekti.filter(o => o.type.toLowerCase().replace('_', '').includes(tip.replace(' ','').toLowerCase()));
			this.naziv = '';
			this.prosecnaOcena = '';
			this.lokacija = '';
		},
		
		pretraziSve: function(){
			this.pretrazeniObjekti = this.objekti
			
			this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => o.name.toLowerCase().includes(this.naziv.toLowerCase()));
			this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => (o.location.address.street.toLowerCase().includes(this.lokacija.toLowerCase()) || o.location.address.city.toLowerCase().includes(this.lokacija.toLowerCase())));
			if(this.prosecnaOcena !== '')
				this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => o.averageRating == this.prosecnaOcena);
			this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => o.type.toLowerCase().replace('_', '').includes(this.tip.replace(' ','').toLowerCase()));
		
		},
		ponistiPretragu: function(){
			this.pretrazeniObjekti = this.objekti;
			this.naziv = '';
			this.prosecnaOcena = '';
			this.tip = '';
			this.lokacija = '';
		},
		sortNaziv: function(){
			if(this.sortiranoNaziv === 0)
			{
				this.pretrazeniObjekti = this.pretrazeniObjekti.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0));
				this.sortiranoNaziv = 1
				this.sortiranoLok = 0
				this.sortiranoOcena =0
			}
			else
			{
				this.pretrazeniObjekti.reverse()
				this.sortiranoNaziv = 0
			}
				
		},
		sortOcena: function(){
			if(this.sortiranoOcena === 0)
			{
				this.pretrazeniObjekti = this.pretrazeniObjekti.sort((a,b) => (a.averageRating > b.averageRating) ? 1 : ((b.averageRating > a.averageRating) ? -1 : 0));
				this.sortiranoNaziv = 0
				this.sortiranoLok = 0
				this.sortiranoOcena = 1
			}
			else
			{
				this.pretrazeniObjekti.reverse()
				this.sortiranoOcena = 1
			}
				
		},
		sortLokacija: function(){
			if(this.sortiranoLok === 0)
			{
				this.pretrazeniObjekti = this.pretrazeniObjekti.sort((a,b) => (a.location.address.street > b.location.address.street) ? 1 : ((b.location.address.street > a.location.address.street) ? -1 : 0));
				this.sortiranoNaziv = 0
				this.sortiranoLok = 1
				this.sortiranoOcena = 0
			}
			else
			{
				this.pretrazeniObjekti.reverse()
				this.sortiranoLok = 0
			}
				
		},
		statusFilter: function(evt){
			var s = evt.target.value;
			if(s == "Izaberi status")
			{
				//this.pretrazeniObjekti = this.objekti
				this.st = '';
			}
			else
				this.st = s;
			
			if(this.tip1 != ''  )
				this.pretrazeniObjekti = this.objekti.filter(o => o.type == this.tip1);
			else
				this.pretrazeniObjekti = this.objekti;
				
			if(this.st != '')	
				this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => o.status == this.st);
		}, 
		
		tipFilter: function(evt){
			var t = evt.target.value;
			if(t == "Izaberi tip")
			{
				//this.pretrazeniObjekti = this.objekti;
				this.tip1 = '';
			}
			else
				this.tip1 = t;	
			
			if(this.tip1 != ''  )
				this.pretrazeniObjekti = this.objekti.filter(o => o.type == this.tip1);
			else
				this.pretrazeniObjekti = this.objekti;
				
			if(this.st != '')	
				this.pretrazeniObjekti = this.pretrazeniObjekti.filter(o => o.status == this.st);	
		},
		
		
		
		pregledaj: function(o){
			event.preventDefault()
			axios.get('rest/sportFacilities/review/' + o.name)
            .then(response => {
                location.href=response.data 
            })
			
		}
		
		
    }
})