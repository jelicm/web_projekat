var app = new Vue({
    el: '#registeredUsers',
    data:{
		registrovaniKorisnici: {},
		registrovani: [],
		registrovaniFiltrirano: [],
		ime: "",
		prezime: "",
		username: "",
		sortiranoIme: 0,
		sortiranoPrezime: 0,
		sortiranoUsername: 0,
		sortiranoPoeni: 0,
		uloge: [],
		tipovi: [],
		tipk: '',
		uloga: ''
    },

	mounted() {
		axios
			.get("rest/users/registeredUsers")
			.then((response) => {
				this.registrovaniKorisnici = response.data;
		    	for (let i = 0, len = this.registrovaniKorisnici.length; i < len; i++) {
					var tip1 = ""
					if(this.registrovaniKorisnici[i].role != "KUPAC")
						tip1 = "NEMA"
			      	this.registrovani.push({
			        user: this.registrovaniKorisnici[i],
					tip: tip1,
					poeni: 0
			      });
			}
			this.registrovaniFiltrirano = this.registrovani;
			axios.get("rest/users/registeredCustomers")
			.then((response1) => {
				for (let i = 0, len = response1.data.length; i < len; i++) {
					var tip1 = response1.data[i].customerType.typeName
			      	this.registrovani.push({
			        user: response1.data[i],
					tip: tip1,
					poeni: response1.data[i].points
			      }); 
				}
			})
			
			this.registrovaniFiltrirano = this.registrovani;
		});
		axios.get("rest/enums/typeName").then((response) => {this.tipovi = response.data})
		axios.get("rest/enums/role").then((response) => {this.uloge = response.data})
	},
	
	methods: {
		pretragaIme: function(naziv1){
			this.registrovaniFiltrirano = this.registrovani.filter(u => u.user.name.toLowerCase().includes(naziv1.toLowerCase()));
			this.username = '';
			this.prezime = '';
		},
		pretragaPrezime: function(naziv2){
			this.registrovaniFiltrirano = this.registrovani.filter(u => u.user.surname.toLowerCase().includes(naziv2.toLowerCase()));
			this.username = '';
			this.ime = '';
		},
		pretragaUsername: function(naziv3){
			this.registrovaniFiltrirano = this.registrovani.filter(u => u.user.username.toLowerCase().includes(naziv3.toLowerCase()));
			this.prezime = '';
			this.ime = '';
		},
		ponistiPretragu: function(){
			this.registrovaniFiltrirano = this.registrovani;
			this.prezime = '';
			this.ime = '';
			this.username = '';
		},
		
		sortIme: function(){
			if(this.sortiranoIme === 0)
			{
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.sort((a,b) => (a.user.name > b.user.name) ? 1 : ((a.user.name < b.user.name) ? -1 : 0));
				this.sortiranoIme = 1
				this.sortiranoPrezime = 0
				this.sortiranoUsername = 0
				this.sortiranoPoeni = 0
			}
			else
			{
				this.registrovaniFiltrirano.reverse()
				this.sortiranoIme = 0
			}
				
		},
		
		sortPrezime: function(){
			if(this.sortiranoPrezime === 0)
			{
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.sort((a,b) => (a.user.surname > b.user.surname) ? 1 : ((a.user.surname < b.user.surname) ? -1 : 0));
				this.sortiranoIme = 0
				this.sortiranoPrezime = 1
				this.sortiranoUsername = 0
				this.sortiranoPoeni = 0
			}
			else
			{
				this.registrovaniFiltrirano.reverse()
				this.sortiranoPrezime = 0
			}
				
		},
		
		sortUsername: function(){
			if(this.sortiranoUsername === 0)
			{
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.sort((a,b) => (a.user.username > b.user.username) ? 1 : ((a.user.username < b.user.username) ? -1 : 0));
				this.sortiranoIme = 0
				this.sortiranoPrezime = 0
				this.sortiranoUsername = 1
				this.sortiranoPoeni = 0
			}
			else
			{
				this.registrovaniFiltrirano.reverse()
				this.sortiranoUsername = 0
			}
				
		},
		sortPoeni: function(){
			if(this.sortiranoPoeni === 0)
			{
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.sort((a,b) => (a.poeni > b.poeni) ? 1 : ((a.poeni < b.poeni) ? -1 : 0));
				this.sortiranoIme = 0
				this.sortiranoPrezime = 0
				this.sortiranoUsername = 0
				this.sortiranoPoeni = 1
			}
			else
			{
				this.registrovaniFiltrirano.reverse()
				this.sortiranoPoeni = 1
			}
				
		},
		
		tipFilter: function(evt){
			
			var t = evt.target.value;
			if(t == "Izaberi tip kupca")
			{
				
				this.tipk = '';
			}
			else
				this.tipk = t;	
			
			if(this.uloga != ''  )
				this.registrovaniFiltrirano = this.registrovani.filter(o => o.user.role == this.uloga);
			else
				this.registrovaniFiltrirano = this.registrovani;
				
			if(this.tipk != '')	
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.filter(o => o.tip == this.tipk);	
			
		},
		ulogeFilter: function(evt){
			var t = evt.target.value;
			if(t == "Izaberi ulogu")
			{
				
				this.uloga = '';
			}
			else
				this.uloga = t;	
			
			if(this.uloga != ''  )
				this.registrovaniFiltrirano = this.registrovani.filter(o => o.user.role == this.uloga);
			else
				this.registrovaniFiltrirano = this.registrovani;
				
			if(this.tipk != '')	
				this.registrovaniFiltrirano = this.registrovaniFiltrirano.filter(o => o.tip == this.tipk);	
		},
		
		obrisiKorisnika: function(k){
			if(k.user.role == 'MENADZER')
			{
				axios.delete("rest/users/deleteManager/" + k.user.username)
				.then (response => {
					this.registrovaniFiltrirano = this.registrovaniFiltrirano.filter(r => r.user.username !== k.user.username);
					this.registrovani = this.registrovani.filter(r => r.user.username !== k.user.username);
				})
				.catch(function(){
                	alert("Ne mozete obrisati mendazera zaduzenog za neki spotski objekat!")
            	})
			}
			if(k.user.role == 'TRENER')
			{
				axios.delete("rest/users/deleteCoach/" + k.user.username)
				.then (response => {
					this.registrovaniFiltrirano = this.registrovaniFiltrirano.filter(r => r.user.username !== k.user.username);
					this.registrovani = this.registrovani.filter(r => r.user.username !== k.user.username);
				})
				
			}
			
		}
		
	}
})