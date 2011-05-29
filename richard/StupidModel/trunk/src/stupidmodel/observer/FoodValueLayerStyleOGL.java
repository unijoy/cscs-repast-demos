package stupidmodel.observer;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

/**
 * @author rlegendi
 * @since Model 3
 */
public class FoodValueLayerStyleOGL implements ValueLayerStyleOGL {

	private ValueLayer layer;

	@Override
	public void init(final ValueLayer layer) {
		this.layer = layer;
	}

	@Override
	public float getCellSize() {
		return 15.0f;
	}

	@Override
	public Color getColor(final double... coordinates) {
		final double food = layer.get(coordinates);

		assert (food >= 0) : String
				.format("A cell's food availability property should be non-negative, but its current value is %d.",
						food);

		final int strength = (int) Math.max(255 - food, 0);
		return new Color(strength, 0xFF, strength); // 0xFFFFFF - white,
													// 0x00FF00 - green
	}

}
