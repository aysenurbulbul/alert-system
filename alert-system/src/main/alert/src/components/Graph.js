import React from 'react'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend} from 'recharts'
import { Grid, Message } from "semantic-ui-react";
import axios from 'axios'


class Graph extends React.Component{
    state = {
        name : '',
        url : '',
        method : '',
        data : [],
        period : 0
    }
    intervalID = 0;
    componentDidMount() {
        let getData = () => {axios.get('http://localhost:8080/alerts/' + this.props.match.params.name)
            .then((response) => {
                //console.log(response.data)
                this.setState({name: response.data.name, url : response.data.url , method : response.data.method, period : response.data.period})
                let results = response.data.results
                let temp = []
                results.forEach((result) => {
                    let date = result.date.split(" ")[1]
                    let situation = result.result
                    let now = {name: date, result: situation, amt: 2000}
                    temp.push(now)
                })
                this.setState({ data : temp})
                this.setState({period:response.data.period})
                //console.log(this.state.period)
                //console.log(temp)
            })
            .catch((error) => {
                console.log(error.message)
            });
        }
        getData();
        this.intervalID = setInterval(() => {
            getData()
        }, 5000);
    }

    componentWillUnmount() {
        clearInterval(this.intervalID);
    }


    render() {
        return (
            <div>
                <Grid centered>
                    <LineChart
                        width={500}
                        height={300}
                        data={this.state.data}
                        margin={{
                            top: 5, right: 30, left: 20, bottom: 5,
                        }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="name" />
                        <YAxis />
                        <Tooltip />
                        <Legend />
                        <Line type="monotone" dataKey="result" stroke="#8884d8" activeDot={{ r: 8 }} />
                    </LineChart>
                </Grid>
                <br/>
                <br/>
                <br/>
                <Grid centered>
                    <Message compact>
                        <b>Name:</b> {this.state.name}
                            <br/>
                        <b>URL:</b> {this.state.url}
                            <br/>
                        <b>Method:</b> {this.state.method}
                            <br/>
                        <b>Period:</b> {this.state.period}
                    </Message>
                </Grid>
            </div>
        );
    }
}

export default Graph;