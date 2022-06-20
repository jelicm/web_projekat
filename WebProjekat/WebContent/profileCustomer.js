var app = new Vue({
    el: '#profileCustomer',
    data:{
        customer: null,
        previous: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInCustomer')
		.then(response => {this.customer = response.data; this.previous = {...this.customer}; this.username = response.data.username})
	},
    methods: {
		update: function(event){
			event.preventDefault()
			if(!this.customer.name || !this.customer.surname || !this.customer.username || !this.customer.password
			 || !this.customer.gender || !this.customer.dateOfBirth){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			axios.put('rest/users/updateCustomer/' + this.username, this.customer)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		},
		restore: function(){
			this.customer = {...this.previous}
		}
    }
})