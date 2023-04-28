package ibf2022.assessment.paf.batch3.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Style;
import ibf2022.assessment.paf.batch3.repositories.BeerRepository;

@Controller
@RequestMapping
public class BeerController {

	@Autowired
	BeerRepository beerResp;

	// TODO Task 2 - view 0
	@GetMapping(path = { "/" })
	public String indexString(Model model) {
		List<Style> styles = beerResp.getStyles();
		model.addAttribute("styles", styles);
		return "view0";
	}

	// TODO Task 3 - view 1
	@GetMapping("/beer/style/{styleId}")
	public String getBreweriesbyBeerStyle(
			@PathVariable(name = "styleId") String styleId,
			@RequestParam(name = "styleName") String styleName,
			Model model) {

		List<Beer> beers = beerResp.getBreweriesByBeer(styleId);
		model.addAttribute("beers", beers);
		model.addAttribute("styleName", styleName);
		return "view1";
	}

	// TODO Task 4 - view 2

	@GetMapping("/beer/{breweryId}")
	public String breweryString(@PathVariable(name = "breweryId") int breweryId, Model model) {
		Optional<Brewery> brewery = beerResp.getBeersFromBrewery(breweryId);
		model.addAttribute("brewery", brewery);
		return "view2";
	}

	@PostMapping("/brewery/{breweryId}/order")
	public String placeOrder(@PathVariable int breweryId,
			@RequestParam Map<String, String> form,
			Model model) {

		Map<String, Integer> order = new HashMap<>();
		for (String key : form.keySet()) {
			if (key.startsWith("beer_")) {
				String beerName = key.substring(5);
				int quantity = Integer.parseInt(form.get(key));
				order.put(beerName, quantity);
			}
		}
		
		return "orderConfirmation";
	}
}
// TODO Task 5 - view 2, place order
