window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#managerMainPage',
	data: {
		manager: {},
		treninzi: {},
		datumi:{},
		naziv: '',
		minCena: 0,
		maxCena: 0,
		datumPocetak: '',
		datumKraj: '',
		treninziSaDatumom: [],
		treninziSaDatumomFiltrirano: [],
		sortiranoCena: 0,
		sortiranoDatum: 0,
		tipt: '',
		tipoviTreninga: {},
	},
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => {
			this.manager = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.manager.sportFacility)
			.then(response => {this.treninzi = response.data})
			axios.get('rest/sportFacilities/getTrainingDates')
			.then(response =>{
				this.datumi = response.data;
				for (let i = 0, len = this.datumi.length; i < len; i++) {
				      this.treninziSaDatumom.push({
				       treninzi: this.treninzi[i],
				       datumi: this.datumi[i]
	      				})
	    		}
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumom;
			})
		
		})
		
		axios.get("rest/enums/trainingType")
			 .then((response) => {this.tipoviTreninga = response.data});
		
	},
	computed: {
	 /* treninziSaDatumom() {
	    const treninziSaDatumom = []
	    for (let i = 0, len = this.datumi.length; i < len; i++) {
	      treninziSaDatumom.push({
	        treninzi: this.treninzi[i],
	        datumi: this.datumi[i]
	      })
	    }
	    return treninziSaDatumom
	  }*/
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(manager = {})
		},
		izmeni: function(tsd){
			axios.get('rest/users/trainingReview/' + tsd.treninzi.name)
		.then(response => {location.href=response.data} )
		},
		
		pretragaCena: function(minCena, maxCena){
			
			if(parseInt(minCena) > parseInt(maxCena)){
				alert("Niste dobro uneli kriterijume pretrage po ceni!")
				this.minCena = 0;
				this.maxCena = 0;
			}
				
			else {
				
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumom.filter(o => o.treninzi.price >= minCena && o.treninzi.price <= maxCena);
				this.naziv = '';
				this.datumPocetak = '';
				this.datumKraj = '';
			}
			
		},
		
		pretragaDatum: function(datumPocetak, datumKraj){
			dp = datumPocetak.split('-')
			dk = datumKraj.split('-')
			
			if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) == parseInt(dk[1]) && parseInt(dp[2]) > parseInt(dk[2])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak = '';
				this.datumKraj = '';
			}
			else if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) > parseInt(dk[1])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak = '';
				this.datumKraj = '';
			}
			else if(parseInt(dp[0]) > parseInt(dk[0])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak = '';
				this.datumKraj = '';
			}

			else {
				
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumom.filter(o => ((parseInt(o.datumi.split(' ')[0].split('.')[2]) > parseInt(dp[0]))||
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) > parseInt(dp[1]))|| 
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dp[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) >= parseInt(dp[2])))
				 && ((parseInt(o.datumi.split(' ')[0].split('.')[2]) < parseInt(dk[0])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) < parseInt(dk[1])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dk[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) <= parseInt(dk[2]))))

				this.naziv = '';
				this.minCena = 0;
				this.maxCena = 0;
				
			}
			
		},
		
		ponistiPretragu: function(){
			this.treninziSaDatumomFiltrirano = this.treninziSaDatumom;
			this.naziv = '';
			this.minCena = 0;
			this.maxCena = 0;
			this.datumPocetak = '';
			this.datumKraj = '';
		},
		
		sortCena: function(){
			if(this.sortiranoCena === 0)
			{
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumomFiltrirano.sort((a,b) => (a.treninzi.price > b.treninzi.price) ? 1 : ((a.treninzi.price < b.treninzi.price) ? -1 : 0));
				
				this.sortiranoCena = 1
				this.sortiranoDatum = 0
			}
			else
			{
				this.treninziSaDatumomFiltrirano.reverse()
				this.sortiranoCena = 0
			}
				
		},
		
		sortDatum: function(){
			if(this.sortiranoDatum === 0)
			{
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumomFiltrirano.sort((a,b) => {
					
					const pa = a.datumi.split(" ")[0].split(".");
					const pb = b.datumi.split(" ")[0].split(".");
					if(parseInt(pa[2]) > parseInt(pb[2]))
						return 1;
					else if(parseInt(pa[2]) < parseInt(pb[2]))
						return -1;
					else if (parseInt(pa[1]) > parseInt(pb[1]))
						return 1;
					else if(parseInt(pa[1]) < parseInt(pb[1]))
						return -1;
					else if (parseInt(pa[0]) > parseInt(pb[0]))
						return 1;
					else if(parseInt(pa[0]) < parseInt(pb[0]))
						return -1;
					else 
					{
						if(a.split(" ")[1].split(":")[1] > b.split(" ")[1].split(":")[1])
							return 1;
						if(a.split(" ")[1].split(":")[1] < b.split(" ")[1].split(":")[1])
							return -1;	
					}
					return 0;
				})
				
				this.sortiranoCena = 0
				this.sortiranoDatum = 1
			}
			else
			{
				this.treninziSaDatumomFiltrirano.reverse()
				this.sortiranoDatum = 0
			}
		},
		
		tipTreningaFilter: function(evt){
			var t = evt.target.value;
			if(t == "Izaberi tip treninga")
			{
				this.tipt = '';
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumom;
			}
			else
			{
				this.tipt = t;
				this.treninziSaDatumomFiltrirano = this.treninziSaDatumom.filter(o => o.treninzi.trainingType == this.tipt);
			}
	
		},
	}
});