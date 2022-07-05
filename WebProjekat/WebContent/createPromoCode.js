var app = new Vue({
    el: '#createPromoCode',
    data:{
        oznaka: '',
		broj: 1,
		popust: 0,
		datum: ''
    },
    methods: {
        create: function(event){
            event.preventDefault()
            if(!this.oznaka || !this.popust || !this.datum || !this.broj){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			if(this.popust > 99){
				alert("Popust(%) nije dobro unet!")
				return
			}
			axios.post('rest/promoCodes/createPromoCode', 
	            {"identifier": this.oznaka, "discount": this.popust, "expirationDate": this.datum, "numberOfUses": this.broj})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime promo koda nije jedinstveno!")
	            })	
        }
    }
})