package ibf2022.assessment.paf.batch3.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Style;

@Repository
public class BeerRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static final String GET_STYLE = "SELECT styles.id AS style_id, styles.style_name, COUNT(beers.id) AS id_count FROM styles LEFT JOIN beers ON styles.id = beers.style_id GROUP BY styles.id, styles.style_name ORDER BY id_count DESC, styles.style_name ASC;";
	// SELECT styles.id AS style_id, styles.style_name, COUNT(beers.id) AS id_count
	// FROM styles
	// LEFT JOIN beers ON styles.id = beers.style_id
	// GROUP BY styles.id, styles.style_name
	// ORDER BY id_count DESC, styles.style_name ASC;
	public static final String GET_BEER_LIST_BY_STYLE = "SELECT beers.id AS beer_id, beers.name AS beer_name, beers.descript AS beer_description, beers.brewery_id AS brewery_id, breweries.name AS brewery_name FROM beers JOIN breweries ON beers.brewery_id = breweries.id WHERE beers.style_id = ? ORDER BY beer_name ASC;";
	// SELECT beers.id AS beer_id, beers.name AS beer_name, beers.descript AS
	// beer_description, beers.brewery_id AS brewery_id, breweries.name AS
	// brewery_name
	// FROM beers
	// JOIN breweries ON beers.brewery_id = breweries.id
	// WHERE beers.style_id = 123
	// ORDER BY beer_name ASC;
	public static final String GET_BREWERY_FROM_BEER = "SELECT beers.id As beer_id, beers.name As beer_name, beers.descript AS beer_description, breweries.id as brewery_id, breweries.name AS brewery_name, breweries.address1,  breweries.address2, breweries.city,  breweries.state,  breweries.code,  breweries.country, breweries.phone, breweries.website, breweries.descript  FROM beers INNER JOIN breweries ON beers.brewery_id = breweries.id WHERE breweries.id = ? ORDER BY beer_name ASC;;";
	// "SELECT beers.id As beer_id, 
	// beers.name As beer_name, beers.descript AS beer_description,
	// breweries.id as brewery_id, 
	// breweries.name AS brewery_name, 
	// breweries.address1,  
	// breweries.address2, 
	// breweries.city,  
	// breweries.state,  
	// breweries.code,  
	// breweries.country,  
	// breweries.phone,  
	// breweries.website  
	
	// FROM beers INNER JOIN breweries ON beers.brewery_id = breweries.id WHERE breweries.id = ? ORDER BY beer_name ASC;";

	// DO NOT CHANGE THE SIGNATURE OF THIS METHOD
	public List<Style> getStyles() {
		// TODO: Task 2
		List<Style> styles = new ArrayList<>();
		SqlRowSet result = jdbcTemplate.queryForRowSet(GET_STYLE);
		while (result.next()) {
			Style style = new Style();
			style.setStyleId(result.getInt("style_id"));
			style.setName(result.getString("style_name"));
			style.setBeerCount(result.getInt("id_count"));
			styles.add(style);
		}

		return styles;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public List<Beer> getBreweriesByBeer(String styleId) {
		// TODO: Task 3
		List<Beer> beers = new ArrayList<>();
		SqlRowSet result = jdbcTemplate.queryForRowSet(GET_BEER_LIST_BY_STYLE, styleId);
		while (result.next()) {
			Beer beer = new Beer();
			beer.setBeerId(result.getInt("beer_id"));
			beer.setBeerName(result.getString("beer_name"));
			beer.setBeerDescription(result.getString("beer_description"));
			beer.setBreweryId(result.getInt("brewery_id"));
			beer.setBreweryName(result.getString("brewery_name"));
			beers.add(beer);
		}

		return beers;
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public Optional<Brewery> getBeersFromBrewery(int id) {
		SqlRowSet result = jdbcTemplate.queryForRowSet(GET_BREWERY_FROM_BEER, String.valueOf(id));

		Brewery brewery = null;
		while (result.next()) {
			if (brewery == null) {
				brewery = new Brewery();
				brewery.setBreweryId(result.getInt("brewery_id"));
				brewery.setName(result.getString("brewery_name"));
				brewery.setAddress1(result.getString("address1"));
				brewery.setAddress2(result.getString("address2"));
				brewery.setCity(result.getString("city"));
				brewery.setPhone(result.getString("phone"));
				brewery.setWebsite(result.getString("website"));
				brewery.setDescription(result.getString("descript"));
			}
			Beer beer = new Beer();
			beer.setBeerId(result.getInt("beer_id"));
			beer.setBeerName(result.getString("beer_name"));
			beer.setBeerDescription(result.getString("beer_description"));
			beer.setBreweryId(result.getInt("brewery_id"));
			beer.setBreweryName(result.getString("brewery_name"));
			brewery.addBeer(beer);
		}

		if (brewery != null) {
			return Optional.of(brewery);
		}
		return Optional.empty();
	}
}
