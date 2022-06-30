var app = new Vue({
    el: '#membershipFee',
    data:{
		popust: '',
		decimala: 0,
		promoKod: ''
    },
    mounted(){
		axios.get('rest/users/loggedInCustomer')
		.then(response => {this.popust = (response.data.customerType.discount*100) + '%'; this.decimala=response.data.customerType.discount})
    },
    methods: {
        create: function(event){
            event.preventDefault()
            axios.post('rest/users/payMembershipFee/'+ this.decimala)
	            .then(response => {
	                location.href=response.data 
	            })
        }
    }
})