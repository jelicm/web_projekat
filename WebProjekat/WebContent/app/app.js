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
		tip1: ''
    },

	mounted() {
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