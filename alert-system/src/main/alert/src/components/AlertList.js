import React from 'react'
import Alert from './Alert'
import axios from 'axios'
import {Grid, Container, Header, Table} from 'semantic-ui-react';

class AlertList extends React.Component{
    state = {
        alerts : []
    }

    componentDidMount() {
        axios.get('http://localhost:8080/alerts')
            .then((response) => {
                console.log(response.data)
                this.setState({alerts : response.data})
            })
            .catch((error) => {
                console.log(error.message)
            })
    }

    handleFromChild = () => {
        axios.get('http://localhost:8080/alerts')
            .then((response) => {
                console.log('from child')
                console.log(response.data)
                this.setState({alerts : response.data})
            })
            .catch((error) => {
                console.log(error.message)
            })
    }

    render(){
        return(
            <Container text>
                <Header as = 'h2' textAlign = 'center'>List of Alerts</Header>
                <br/>
                <Grid centered>
                    <Table basic='very' celled collapsing>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Name</Table.HeaderCell>
                                <Table.HeaderCell>URL</Table.HeaderCell>
                                <Table.HeaderCell>Method</Table.HeaderCell>
                                <Table.HeaderCell>Period</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Alert alerts = {this.state.alerts} handler = {this.handleFromChild}/>
                    </Table>
                </Grid>
            </Container>
        )
    }
}

export default AlertList;