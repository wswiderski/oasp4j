package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.general.common.base.AbstractRestServiceTest;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
import io.oasp.gastronomy.restaurant.offermanagement.service.api.rest.OffermanagementRestService;
import io.oasp.gastronomy.restaurant.salesmanagement.service.api.rest.SalesmanagementRestService;
import io.oasp.module.service.common.api.client.ServiceClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

/**
 * This class serves as an example of how to perform a subsystem test (e.g., call a *RestService interface). The test
 * database is accessed via an instance of the class {@link SalesmanagementRestService}.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OffermanagementRestTestConfig.class})
@TestPropertySource(properties = {"flyway.locations=filesystem:src/test/resources/db/tablemanagement",
  "service.client.app.restaurant.user.login=waiter"})

public class OffermanagmentRestServiceTest extends AbstractRestServiceTest {

  private OffermanagementRestService service;

  @Inject
  private ServiceClientFactory serviceClientFactory;

  @Inject
  private OffermanagementRestServiceTestHelper helper;

  /**
   * Provides initialization previous to the creation of the text fixture.
   */
  @Override
  public void doSetUp() {

    super.doSetUp();
    getDbTestHelper().resetDatabase();
    this.service = this.serviceClientFactory.create(OffermanagementRestService.class);
  }

  /**
   * Provides clean up after tests.
   */
  @Override
  public void doTearDown() {

    this.service = null;
    super.doTearDown();
  }

  @Test
  public void testShouldGetSpecial() {
    SpecialEto special = this.helper.createSpecialEto(null);
    SpecialEto createdSpecial = this.service.saveSpecial(special);
    assertThat(createdSpecial).isNotNull();

    // when
    SpecialEto response = this.service.getSpecial(createdSpecial.getId());

    // then
    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(createdSpecial.getId());
    assertThat(response.getName()).isEqualTo(helper.DUMMY_NAME);
  }

  @Test
  public void testShouldSaveSpecial() {
    // given:
    SpecialEto specialEto = helper.createSpecialEto(null);
    //when
    SpecialEto createdSpecial = this.service.saveSpecial(specialEto);
    // then

    SpecialEto response = this.service.getSpecial(createdSpecial.getId());

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(createdSpecial.getId());
    assertThat(response.getName()).isEqualTo(helper.DUMMY_NAME);
  }

  @Test
  public void testShouldUpdateSpecial() {
    // given:
    String NEW_NAME = "newName";
    SpecialEto specialEto = helper.createSpecialEto(null);
    SpecialEto createdSpecial = this.service.saveSpecial(specialEto);

    //when
    createdSpecial.setName(NEW_NAME);
    this.service.updateSpecial(createdSpecial);

    // then:
    SpecialEto response = this.service.getSpecial(createdSpecial.getId());
    assertThat(response.getName()).isEqualTo(NEW_NAME);
  }

  @Test
  public void testShouldDeleteSpecial() {
    // given:
    SpecialEto specialEto = helper.createSpecialEto(null);
    SpecialEto createdSpecial = this.service.saveSpecial(specialEto);

    assertThat(this.service.getSpecial(createdSpecial.getId())).isNotNull();

    // when:
    this.service.deleteSpecial(createdSpecial.getId());
    SpecialEto response = this.service.getSpecial(createdSpecial.getId());
    //then
    assertThat(this.service.getSpecial(createdSpecial.getId())).isNull();
  }

}
