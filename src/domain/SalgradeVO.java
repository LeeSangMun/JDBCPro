package domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@Builder - 값을 안주면 오류
public class SalgradeVO {
	private int grade;
	private int losal;
	private int hisal;

	@Override
	public String toString() {
		return "SalgradeVO [grade=" + grade + ", losal=" + losal + ", hisal=" + hisal + "]";
	}

}