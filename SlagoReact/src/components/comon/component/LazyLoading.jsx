import React, { Component } from 'react';
import styled  from 'styled-components';

class LazyLoading extends Component {
    render() {
        return (
            <Container>
                <LoadingImg src={this.props.src}/>
            </Container>
        );
    }
}

const Container = styled.div`
    width:100vw;
    height:100vh;
    background-color: #ffffff;
    text-align: center;
`

const LoadingImg = styled.img`
    margin-top:35vh;
    height:30vh;
`

export default LazyLoading;