var app = new Vue({
    el: '#membershipFee',
    data:{
		popust: '',
		procenat: 0,
		promoKod: ''
    },
    mounted(){
		axios.get('rest/users/loggedInCustomer')
		.then(response => {this.popust = response.data.customerType.discount + '%'; this.procenat=response.data.customerType.discount})
    },
    methods: {
        create: function(event){
            event.preventDefault()
            axios.post('rest/users/payMembershipFee/'+ this.procenat)
	            .then(response => {
	                location.href=response.data 
	            })
        }
    }
})