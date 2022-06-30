var app = new Vue({
    el: '#membershipFee',
    data:{
		tip: '',
		paket: '',
		vazenje: '',
		termini: '',
		cena: ''
    },
    methods: {
        promena: function(){
            if(this.paket == 'Osnovni'){
				this.tip = 'MESECNA'
				this.vazenje = '1 mesec'
				this.termini = '30'
				this.cena = '100'
			}
			else if(this.paket == 'Srednji'){
				this.tip = 'MESECNA'
				this.vazenje = '3 meseca'
				this.termini = '90'
				this.cena = '300'
			}
			else{
				this.tip = 'GODISNJA'
				this.vazenje = '12 meseci'
				this.termini = 'Neograniceno'
				this.cena = '1000'
			}
        },
        create: function(event){
            event.preventDefault()
            var today = new Date();
			var paymentDate = today.getDate()+'.'+(today.getMonth()+1)+'.'+today.getFullYear()+'.';
			var expirationDateAndTime;
			if(this.paket == 'Osnovni'){
				expirationDateAndTime = expirationDateAndTime = (today.getDate()-1)+'.'+(today.getMonth()+2)+'.'+today.getFullYear()+'.';
			}
			else if(this.paket == 'Srednji'){
				expirationDateAndTime = (today.getDate()-1)+'.'+(today.getMonth()+4)+'.'+today.getFullYear()+'.';
			}
			else{
				expirationDateAndTime = today.getDate()+'.'+(today.getMonth()+1)+'.'+(today.getFullYear()+1)+'.';
			}
            axios.post('rest/users/createMembershipFee', 
	            {"identifier": 'clanarina', "membershipFeeType": this.tip, "paymentDate": paymentDate, "expirationDateAndTime" : expirationDateAndTime + ' 12:00',
	            "price" : this.cena, "customer" : null, "membershipFeeStatus": 'NEAKTIVNA', "numberOfAppointments": this.termini})
	            .then(response => {
	                location.href=response.data 
	            })
        }
    }
})