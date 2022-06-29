window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#customerMainPage',
	data: {
		customer: {},
		treninzi: {},
		datumi: {},
		tipovi: {},
		naziv: '',
		minCena: 0,
		maxCena: 0,
		datumPocetak: '',
		datumKraj: '',
		treninziSaDatumom: [],
		treninziSaDatumomFiltrirano: [],
		
	},
	mounted() {
		axios.get('rest/users/loggedInCustomer')
		.then(response => (this.customer = response.data))
		axios.get('rest/users/getTrainingsForCustomer')
		.then(response => (this.treninzi = response.data))
		axios.get('rest/users/getTrainingDates')
		.then(response => (this.datumi = response.data))
		axios.get('rest/users/getSportFacilityType')
		.then(response => {
			this.tipovi = response.data;
		    for (let i = 0, len = this.datumi.length; i < len; i++) {
		      this.treninziSaDatumom.push({
		        treninzi: this.treninzi[i],
		        datumi: this.datumi[i],
		        tipovi: this.tipovi[i]
		      });
			}
			this.treninziSaDatumomFiltrirano = this.treninziSaDatumom;

		})
	},
	/*computed: {
	  treninziSaDatumom() {
	    const treninziSaDatumom = []
	    for (let i = 0, len = this.datumi.length; i < len; i++) {
	      treninziSaDatumom.push({
	        treninzi: this.treninzi[i],
	        datumi: this.datumi[i],
	        tipovi: this.tipovi[i]
	      })
	    }
	    return treninziSaDatumom
	  }
	},*/
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(customer = {})
		},
		
		pretragaNaziv: function(naziv){
			this.treninziSaDatumomFiltrirano = this.treninziSaDatumom.filter(o => o.treninzi.sportFacility.toLowerCase().includes(naziv.toLowerCase()));
			this.minCena = 0;
			this.maxCena = 0;
			this.datumPocetak = '';
			this.datumKraj = '';
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
	}
});