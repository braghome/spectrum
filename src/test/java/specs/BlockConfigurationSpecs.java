package specs;

import static com.greghaskins.spectrum.Configure.ignore;
import static com.greghaskins.spectrum.Configure.with;
import static com.greghaskins.spectrum.dsl.specification.Specification.describe;
import static com.greghaskins.spectrum.dsl.specification.Specification.it;
import static com.greghaskins.spectrum.dsl.specification.Specification.let;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.greghaskins.spectrum.Spectrum;
import com.greghaskins.spectrum.SpectrumHelper;
import com.greghaskins.spectrum.internal.configuration.BlockConfiguration;

import org.junit.runner.Result;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

/**
 * Demonstrate how to focus and ignore specs using
 * {@link BlockConfiguration}.
 */
@RunWith(Spectrum.class)
public class BlockConfigurationSpecs {
  {
    describe("The ignore() precondition", () -> {

      describe("at the suite level", () -> {
        Supplier<Result> result = let(() -> SpectrumHelper.run(() -> {

          describe("Has ignored suite", with(ignore(), () -> {
            it("will not run this spec", () -> {
            });
            it("or this spec", () -> {
            });
          }));
        }));

        it("marks all its specs as ignored", () -> {
          assertThat(result.get().getIgnoreCount(), is(2));
        });

      });

      describe("at the spec level", () -> {

        Supplier<Result> result = let(() -> SpectrumHelper.run(() -> {

          it("is not ignored", () -> {
          });

          it("is ignored", with(ignore(), () -> {
          }));

          it("is ignored for a reason", with(ignore("not important for this release"), () -> {
          }));

          it("is a block ignored as a block", ignore(() -> {
          }));

          it("is a block ignored as a block for a reason", ignore("Not ready yet", () -> {
          }));

        }));

        it("marks those specs as ignored", () -> {
          assertThat(result.get().getIgnoreCount(), is(4));
        });
      });

    });
  }
}
