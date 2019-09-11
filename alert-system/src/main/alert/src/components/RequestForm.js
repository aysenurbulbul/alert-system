import React from 'react';
import axios from 'axios';
import validator from 'validator'
import {Form, Input, Grid, Select, Container, Header, Message} from 'semantic-ui-react';

const options = [
    { key: 'e', text: 'Method', value: '' },
    { key: 'g', text: 'GET', value: 'GET' },
    { key: 'p', text: 'POST', value: 'POST' },
    { key: 'd', text: 'DELETE', value: 'DELETE' },
    { key: 'u', text: 'PUT', value: 'PUT' },
]

class RequestForm extends React.Component{
    state = {
        name : '',
        url : '',
        method : '',
        period : 0,
        message : '',
        situation : 'grey',
        display : 'none',
        nameError : false,
        urlError : false,
        methodError : false,
        periodError : false
    }

    handleSubmit = (event) => {
        event.preventDefault()
        //create alert
        axios.post('http://localhost:8080/alerts', {
            name : this.state.name,
            url : this.state.url,
            method : this.state.method,
            period : this.state.period,
            results : []
        }).then((response) => {
            if(response.status===200)
                this.setState({message : 'Alert is added!', situation : 'green', display:''})
        })
            .catch((error) => {
                console.log(error.message);
                this.setState({message: error.message, situation : 'red', display:''})
            })
        this.setState({
            name : '',
            url : '',
            method : '',
            period : 0
        })
    }
    handleValidation = (name) => {
        //validation
        if((name === 'name') && (this.state.name === '')){
            this.setState({nameError : true, message : "Please enter a name", situation : 'red', display : ''})
        } else if(name === 'name') {
            this.setState({nameError : false, message : '', display : 'none'})
        }

        if((name === 'url') && (!validator.isURL(this.state.url))){
            this.setState({urlError : true, message : "Invalid URL", situation : 'red', display : ''})
        } else if(name === 'url') {
            this.setState({urlError : false, message : '', display : 'none'})
        }

        if((name === 'method') && (this.state.method === '')){
            this.setState({methodError : true, message : "Please select a method", situation : 'red', display : ''})
        } else if(name === 'method') {
            this.setState({methodError : false, message : '', display : 'none'})
        }

        if((name === 'period') && (!validator.isNumeric(this.state.period))){
            this.setState({periodError : true, message : "Please enter a numeric value", situation : 'red', display : ''})
        } else if(name === 'period') {
            this.setState({periodError : false, message : '', display : 'none'})
        }
    }
    handleChange = (e, {name, value}) => {
        //pass it callback or component did update
        this.setState({[name] : value},()=>{this.handleValidation(name)})

    }
    render() {
        return (
            <Container text>
                <Header as = 'h2' textAlign = 'center'>Alert Form</Header>
                <br/>
                <Form onSubmit = {this.handleSubmit}>
                    <Grid columns = 'two' divided centered>
                        <Grid.Row>
                            <Grid.Column width={4}>
                                <label>Name</label>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Input
                                    name = 'name'
                                    value = {this.state.name}
                                    onChange = {this.handleChange}
                                />
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                    <Grid columns = 'two' divided centered>
                        <Grid.Row>
                            <Grid.Column width={4}>
                                <label>URL</label>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Input
                                    name = 'url'
                                    value = {this.state.url}
                                    onChange = {this.handleChange}
                                />
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                    <Grid columns = 'two' divided centered>
                        <Grid.Row>
                            <Grid.Column width={4}>
                                <label>HTTP Method</label>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Select
                                    options = {options}
                                    placeholder = 'Method'
                                    name = 'method'
                                    value = {this.state.method}
                                    onChange = {this.handleChange}
                                />
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                    <Grid columns = 'two' divided centered>
                        <Grid.Row>
                            <Grid.Column width={4}>
                                <label>Period</label>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Input
                                    name = 'period'
                                    value = {this.state.period}
                                    onChange = {this.handleChange}
                                />
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                    <Grid columns = 'two' divided centered>
                        <Grid.Row>
                            <Grid.Column width = {4}>
                            </Grid.Column>
                            <Grid.Column width = {5}>
                                <Form.Button
                                    secondary
                                    content = 'Submit'
                                    disabled = {!this.state.name
                                    || !validator.isURL(this.state.url)
                                    || !this.state.method
                                    || (!this.state.period || !validator.isNumeric(this.state.period))}
                                />
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                </Form>
                <div style = {{display: this.state.display}}>
                    <Grid centered>
                        <Grid.Row>
                            <Message
                                color = {this.state.situation}
                                content = {this.state.message}
                            />
                        </Grid.Row>
                    </Grid>
                </div>
            </Container>
        )
    }
}

export default RequestForm;