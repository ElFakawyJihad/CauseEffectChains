
public class ExampleClass {

	public int number = 0;
	
	public ExampleClass(int number) {
		this.number = number;
	}
	
	public void add_ten_to_number() {
		this.number += 10;
	}
	
	public void divide_number_by_two() {
		this.number /= 2;
	}
	
	public void multiply_number_by_himself() {
		this.number *= this.number;
	}
	
	public static void main(String[] args) {
		ExampleClass example = new ExampleClass(5);
		example.multiply_number_by_himself();
		example.add_ten_to_number();
		example.divide_number_by_two();
		System.out.println(example.number);
	}

}
