var app = new Vue({
    el: '#profileCustomer',
    data:{
        customer: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInCustomer')
		.then(response => {this.customer = response.data; this.username = response.data.username})
	},
    methods: {
		update: function(){
			axios.put('rest/users/updateCustomer/'+this.username, this.customer)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		}
    }
})