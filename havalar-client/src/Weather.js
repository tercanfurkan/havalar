import React, { useState, useEffect } from 'react';
import {
  TextField,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Typography,
  Grid,
  Paper,
} from '@mui/material';
import { Search as SearchIcon, ArrowForward as ArrowForwardIcon } from '@mui/icons-material';

const Weather = () => {
  const [searchText, setSearchText] = useState('');
  const [data, setData] = useState([]);

  const handleSearchChange = (event) => {
    setSearchText(event.target.value);
  };

  const fetchInitialData = async () => {
    try {
      const response = await fetch('/all'); // Fetch initial data from "/all" endpoint
      const initialData = await response.json();
      setData(initialData);
    } catch (error) {
      console.error('Error fetching initial data:', error);
    }
  };

  useEffect(() => {
    fetchInitialData(); // Fetch initial data when component mounts
  }, []);

  const filteredData = data.filter((item) =>
    item.cityName.toLowerCase().startsWith(searchText.toLowerCase())
  );

  return (
    <Grid container direction="column" alignItems="center" spacing={2}>
      <Grid item>
        <Typography variant="h4" align="center">
          Weather App
        </Typography>
      </Grid>
      <Grid item xs={12}>
        <TextField
          fullWidth
          variant="outlined"
          label="Search City"
          value={searchText}
          onChange={handleSearchChange}
          InputProps={{
            endAdornment: (
              <IconButton>
                <SearchIcon />
              </IconButton>
            ),
          }}
        />
      </Grid>
      <Grid item xs={12}>
        <List>
          <ListItem component={Paper} elevation={3}>
            <ListItemText primary="City" secondary="Temperature" style={{ marginRight: '48px' }} />
            <ListItemText secondary="Wind Speed (ms)" style={{ marginRight: '32px' }} />
            <ListItemText secondary="Wind Direction (°)" />
          </ListItem>
            {filteredData.map((item, index) => (
              <ListItem key={index} component={Paper} elevation={3}>
                <ListItemText primary={item.cityName} secondary={`${item.temperature}`} style={{ marginRight: '48px' }} />
                <ListItemText secondary={`${item.windSpeedMs}`} style={{ marginRight: '32px' }} />
                <ListItemText secondary={`${item.windDirection}°`} />
                <ListItemSecondaryAction>
                  <IconButton>
                    <ArrowForwardIcon style={{ transform: `rotate(${item.windDirection}deg)` }} />
                  </IconButton>
                </ListItemSecondaryAction>
              </ListItem>
            ))}
        </List>
      </Grid>
      <Grid item>
        <Typography variant="body2" align="center">
          © 2023 Weather App
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Weather;